package com.utils;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 协同过滤推荐工具（支持用户协同 + 物品协同）
 * 支持用户协同过滤（UserCF） 和物品协同过滤（ItemCF），核心是基于余弦相似度计算偏好相似度，最终生成个性化推荐列表
 * @author GG Bond
 */
public class UserBasedCollaborativeFiltering {

    private static final Logger logger = LoggerFactory.getLogger(UserBasedCollaborativeFiltering.class);

    public UserBasedCollaborativeFiltering() {
    }

    /**
     * 推荐物品入口
     */
    public List<String> recommendItems(List<UserBehavior> entities, String currentUserId, int numRecommendations, boolean isUserBased) {
        try {
            List<String> targetObjects = getTargetObjects(entities, currentUserId, isUserBased);
            Map<String, Map<String, Double>> matrix = buildSparseMatrix(entities, isUserBased);
            Map<String, Double> similarityScores = calculateSimilarityScores(matrix, targetObjects);
            return generateRecommendations(matrix, similarityScores, currentUserId, numRecommendations, isUserBased);
        } catch (Exception e) {
            logger.error("推荐异常", e);
            return new ArrayList<>();
        }
    }

    private List<String> getTargetObjects(List<UserBehavior> entities, String currentUserId, boolean isUserBased) {
        if (isUserBased) {
            return Collections.singletonList(currentUserId);
        } else {
            return entities.stream()
                    .filter(e -> currentUserId.equals(e.getUserId()))
                    .map(UserBehavior::getItemId)
                    .distinct()
                    .collect(Collectors.toList());
        }
    }

    private Map<String, Map<String, Double>> buildSparseMatrix(List<UserBehavior> entities, boolean isUserBased) {
        Map<String, Map<String, Double>> matrix = new HashMap<>();
        for (UserBehavior e : entities) {
            String row = isUserBased ? e.getUserId() : e.getItemId();
            String col = isUserBased ? e.getItemId() : e.getUserId();
            matrix.computeIfAbsent(row, k -> new HashMap<>()).put(col, e.getScore());
        }
        return matrix;
    }

    private Map<String, Double> calculateSimilarityScores(Map<String, Map<String, Double>> matrix, List<String> targetObjectIds) {
        Map<String, Double> currentPrefs = new HashMap<>();
        for (String id : targetObjectIds) {
            Map<String, Double> prefs = matrix.get(id);
            if (prefs != null) {
                currentPrefs.putAll(prefs);
            }
        }

        Map<String, Double> similarityScores = new HashMap<>();
        if (!CollectionUtils.isEmpty(currentPrefs)) {
            for (Map.Entry<String, Map<String, Double>> entry : matrix.entrySet()) {
                String otherId = entry.getKey();
                if (!targetObjectIds.contains(otherId)) {
                    double sim = cosineSimilarity(currentPrefs, entry.getValue());
                    if (sim > 0) {
                        similarityScores.put(otherId, sim);
                    }
                }
            }
        }
        return similarityScores;
    }

    private double cosineSimilarity(Map<String, Double> p1, Map<String, Double> p2) {
        Set<String> common = new HashSet<>(p1.keySet());
        common.retainAll(p2.keySet());
        if (common.isEmpty()) {
            return 0.0;
        }

        double dot = common.stream().mapToDouble(k -> p1.get(k) * p2.get(k)).sum();
        double n1 = Math.sqrt(p1.values().stream().mapToDouble(v -> v * v).sum());
        double n2 = Math.sqrt(p2.values().stream().mapToDouble(v -> v * v).sum());

        return n1 == 0 || n2 == 0 ? 0.0 : dot / (n1 * n2);
    }

    private List<String> generateRecommendations(Map<String, Map<String, Double>> matrix,
                                                 Map<String, Double> similarityScores,
                                                 String currentUserId,
                                                 int limit,
                                                 boolean isUserBased) {
        Map<String, Double> itemScores = new HashMap<>();
        Map<String, Double> userItems = matrix.getOrDefault(currentUserId, Collections.emptyMap());

        for (Map.Entry<String, Double> entry : similarityScores.entrySet()) {
            String similarId = entry.getKey();
            double score = entry.getValue();
            Map<String, Double> items = matrix.get(similarId);
            if (items == null) continue;

            for (Map.Entry<String, Double> itemEntry : items.entrySet()) {
                String item = itemEntry.getKey();
                if (!userItems.containsKey(item)) {
                    String recommendId = isUserBased ? item : similarId;
                    itemScores.merge(recommendId, score, Double::sum);
                }
            }
        }

        return itemScores.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Data
    public static class UserBehavior {
        private String userId;
        private String itemId;
        private Double score;

        public UserBehavior(String userId, String itemId, Double score) {
            this.userId = userId;
            this.itemId = itemId;
            this.score = score;
        }

        public UserBehavior(String userId, String itemId) {
            this.userId = userId;
            this.itemId = itemId;
            this.score = 1.0;
        }
    }
}