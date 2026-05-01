package com.utils;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户基于协同过滤推荐算法实现类
 */
public class UserBasedCollaborativeFiltering {

    private static final Logger logger = LoggerFactory.getLogger(UserBasedCollaborativeFiltering.class);


    // 存储用户对物品的评分
    private Map<String, Map<String, Double>> userRatings;
    // 存储物品被哪些用户评分过
    private Map<String, List<String>> itemUsers;
    // 用户到索引的映射
    private Map<String, Integer> userIndex;
    // 索引到用户的映射
    private Map<Integer, String> indexUser;
    // 存储用户之间相似度的稀疏矩阵
    private Long[][] sparseMatrix;

    /**
     * 构造函数，初始化用户评分数据并构建稀疏矩阵
     *
     * @param userRatings 用户对物品的评分
     */
    public UserBasedCollaborativeFiltering(Map<String, Map<String, Double>> userRatings) {
        this.userRatings = userRatings;
        this.itemUsers = new HashMap<>();

        this.userIndex = new HashMap<>();//辅助存储每一个用户的用户索引index映射:user->index
        this.indexUser = new HashMap<>();//辅助存储每一个索引index对应的用户映射:index->user

        // 构建物品-用户倒排表
        int keyIndex = 0;
        for (String user : userRatings.keySet()) {
            Map<String, Double> ratings = userRatings.get(user);
            for (String item : ratings.keySet()) {
                if (!itemUsers.containsKey(item)) {
                    itemUsers.put(item, new ArrayList<>());
                }
                itemUsers.get(item).add(user);
            }
            //用户ID与稀疏矩阵建立对应关系
            this.userIndex.put(user, keyIndex);
            this.indexUser.put(keyIndex, user);
            keyIndex++;
        }

        int N = userRatings.size();
        this.sparseMatrix = new Long[N][N];//建立用户稀疏矩阵，用于用户相似度计算【相似度矩阵】
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                this.sparseMatrix[i][j] = (long) 0;
        }
        for (String item : itemUsers.keySet()) {
            List<String> userList = itemUsers.get(item);
            for (String u1 : userList) {
                for (String u2 : userList) {
                    if (u1.equals(u2)) {
                        continue;
                    }
                    this.sparseMatrix[this.userIndex.get(u1)][this.userIndex.get(u2)] += 1;
                }
            }
        }

    }

    /**
     * 计算两个用户的相似度
     *
     * @param user1 用户1
     * @param user2 用户2
     * @return 两个用户的相似度
     */
    public double calculateSimilarity(String user1, String user2) {
        Integer id1 = this.userIndex.get(user1);
        Integer id2 = this.userIndex.get(user2);
        if (id1 == null || id2 == null) return 0.0;
        return this.sparseMatrix[id1][id2] / Math.sqrt(userRatings.get(indexUser.get(id1)).size() * userRatings.get(indexUser.get(id2)).size());
    }

    /**
     * 为目标用户推荐物品
     *
     * @param targetUser         目标用户
     * @param numRecommendations 推荐物品的数量
     * @return 推荐的物品列表
     */
    public List<String> recommendItems(String targetUser, int numRecommendations) {
        // 计算目标用户与其他用户的相似度
        Map<String, Double> userSimilarities = new HashMap<>();
        for (String user : userRatings.keySet()) {
            if (!user.equals(targetUser)) {
                double similarity = calculateSimilarity(targetUser, user);
                userSimilarities.put(user, similarity);
            }
        }

        // 对用户相似度进行降序排序
        List<Map.Entry<String, Double>> sortedSimilarities = new ArrayList<>(userSimilarities.entrySet());
        sortedSimilarities.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // 选择最相似的用户作为推荐来源
        List<String> similarUsers = new ArrayList<>();
        for (int i = 0; i < numRecommendations; i++) {
            if (i < sortedSimilarities.size()) {
                similarUsers.add(sortedSimilarities.get(i).getKey());
            } else {
                break;
            }
        }

        // 根据最相似用户喜欢的物品来生成推荐列表
        Map<String, Double> recommendations = new HashMap<>();
        for (String user : similarUsers) {
            Map<String, Double> ratings = userRatings.get(user);
            for (String item : ratings.keySet()) {
                if (userRatings.get(targetUser) != null && !userRatings.get(targetUser).containsKey(item)) {
                    recommendations.put(item, ratings.get(item));
                }
            }
        }

        // 对推荐物品进行排序并返回前N个
        LinkedHashMap<String, Double> sortedRecommendations = new LinkedHashMap<>(recommendations);
        int numItems = Math.min(numRecommendations, sortedRecommendations.size());
        sortedRecommendations = sortedRecommendations.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Double>comparingByValue().reversed())).limit(numItems)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        List<String> result = new ArrayList<String>();
        result.addAll(sortedRecommendations.keySet());
        return result;
    }

///////////////////////////////////////////////以下为新版本协同推荐算法逻辑///////////////////////////////////////////////////////

    public UserBasedCollaborativeFiltering() {

    }

    /**
     * 推荐商品
     *
     * @param entities           用户行为实体列表
     * @param currentUserId      当前用户ID
     * @param numRecommendations 推荐数量
     * @param isUserBased        推荐模式 true：基于用户协同过滤，false：基于物品协同过滤
     * @return 推荐的商品ID列表
     */
    public List<String> recommendItems(List<UserBehavior> entities, String currentUserId, int numRecommendations, boolean isUserBased) {
        List<String> recommendations = new ArrayList<>();
        try {
            logger.info("开始为用户 {} 推荐物品，模式: {}", currentUserId, isUserBased ? "用户协同" : "物品协同");

            // 获取目标对象（用户或物品）
            List<String> targetObjects = getTargetObjects(entities, currentUserId, isUserBased);

            // 构建稀疏矩阵
            Map<String, Map<String, Double>> matrix = buildSparseMatrix(entities, isUserBased);

            // 计算相似度得分
            Map<String, Double> similarityScores = calculateSimilarityScores(matrix, targetObjects);

            // 生成推荐列表
            recommendations = generateRecommendations(matrix, similarityScores, currentUserId, numRecommendations, isUserBased);

            logger.info("成功为用户 {} 推荐了 {} 个物品", currentUserId, recommendations.size());
        } catch (Exception e) {
            logger.error("在推荐物品时发生异常", e);
        }
        return recommendations;
    }

    /**
     * 获取目标对象列表（用于后续相似度计算）
     *
     * @param entities      用户行为数据列表
     * @param currentUserId 当前用户ID
     * @param isUserBased   推荐模式，true表示基于用户，false表示基于物品
     * @return 目标对象ID列表。若为用户协同，则返回当前用户；若为物品协同，则返回该用户交互过的所有物品
     */
    private List<String> getTargetObjects(List<UserBehavior> entities, String currentUserId, boolean isUserBased) {
        if (isUserBased) {
            return Arrays.asList(currentUserId);
        } else {
            return entities.stream()
                    .filter(entity -> currentUserId.equals(entity.getUserId()))
                    .map(UserBehavior::getItemId)
                    .distinct()
                    .collect(Collectors.toList());
        }
    }

    /**
     * 构建稀疏矩阵，将用户-物品评分数据转换成以用户或物品为主键的二维映射结构
     *
     * @param entities    用户行为数据列表
     * @param isUserBased 推荐模式，true表示按用户构建，false表示按物品构建
     * @return 稀疏矩阵，格式为 Map<主键, Map<次键, 评分>>
     */
    private Map<String, Map<String, Double>> buildSparseMatrix(List<UserBehavior> entities, boolean isUserBased) {
        Map<String, Map<String, Double>> matrix = new HashMap<>();
        for (UserBehavior entity : entities) {
            String rowKey = isUserBased ? entity.getUserId() : entity.getItemId();
            String colKey = isUserBased ? entity.getItemId() : entity.getUserId();
            matrix.computeIfAbsent(rowKey, k -> new HashMap<>()).put(colKey, entity.getScore());
        }
        return matrix;
    }


    /**
     * 计算与目标对象之间的相似度得分
     *
     * @param matrix          稀疏矩阵，存储用户/物品的行为偏好数据
     * @param targetObjectIds 目标对象ID列表（如当前用户ID或已交互物品ID列表）
     * @return 其他对象与其相似度的映射表
     */
    private Map<String, Double> calculateSimilarityScores(Map<String, Map<String, Double>> matrix, List<String> targetObjectIds) {
        Map<String, Double> currentUserPrefs = new HashMap<>();
        Map<String, Double> similarityScores = new HashMap<>();
        // 合并多个目标对象的偏好信息（例如多个已知物品的评分）
        for (String targetObjectId : targetObjectIds) {
            Map<String, Double> prefs = matrix.get(targetObjectId);
            // 添加空值检查，防止 NullPointerException
            if (prefs != null) {
                currentUserPrefs.putAll(prefs);
            }
        }

        if (!CollectionUtils.isEmpty(currentUserPrefs)) {
            for (Map.Entry<String, Map<String, Double>> entry : matrix.entrySet()) {
                String otherUserid = entry.getKey();
                // 跳过自身
                if (!targetObjectIds.contains(otherUserid)) {
                    Map<String, Double> otherUserPrefs = entry.getValue();
                    double similarity = calculateCosineSimilarity(currentUserPrefs, otherUserPrefs);
                    if (similarity > 0) {
                        similarityScores.put(otherUserid, similarity);
                    }
                }
            }
        }
        return similarityScores;
    }

    /**
     * 计算余弦相似度
     *
     * @param prefs1 用户偏好
     * @param prefs2 另一个用户偏好
     * @return 余弦相似度
     */
    private double calculateCosineSimilarity(Map<String, Double> prefs1, Map<String, Double> prefs2) {
        // 计算两个用户的共同偏好商品
        Set<String> commonKeys = new HashSet<>(prefs1.keySet());
        commonKeys.retainAll(prefs2.keySet());

        // 如果没有共同偏好，则相似度为0
        if (commonKeys.isEmpty()) {
            return 0.0;
        }

        // 计算点积
        double dotProduct = commonKeys.stream()
                .mapToDouble(key -> prefs1.get(key) * prefs2.get(key))
                .sum();

        // 计算两个用户的偏好向量的范数
        double normUser1 = Math.sqrt(prefs1.values().stream().mapToDouble(i -> i * i).sum());
        double normUser2 = Math.sqrt(prefs2.values().stream().mapToDouble(i -> i * i).sum());

        // 防止除零错误
        if (normUser1 == 0 || normUser2 == 0) {
            return 0.0;
        }

        // 返回余弦相似度
        return dotProduct / (normUser1 * normUser2);
    }

    /**
     * 基于相似用户或物品生成最终推荐结果
     *
     * @param xMatrix            行为稀疏矩阵
     * @param similarityScores   相似对象及其相似度得分
     * @param currentUserid      当前用户ID
     * @param numRecommendations 最大推荐数量
     * @param model              推荐模型类型，true为用户协同，false为物品协同
     * @return 推荐的商品ID列表
     */
    public List<String> generateRecommendations(Map<String, Map<String, Double>> xMatrix, Map<String, Double> similarityScores, String currentUserid, int numRecommendations, boolean model) {
        Map<String, Double> productScores = new HashMap<>();

        logger.info("开始为用户 {} 生成推荐列表", currentUserid);

        for (Map.Entry<String, Double> similarUser : similarityScores.entrySet()) {
            String xSimilar = similarUser.getKey();
            double similarityScore = similarUser.getValue();
            Map<String, Double> xSimilarPrefs = xMatrix.get(xSimilar);

            for (Map.Entry<String, Double> productEntry : xSimilarPrefs.entrySet()) {
                String y = productEntry.getKey();
                // 过滤掉当前用户已经交互过的项目
                if (!xMatrix.getOrDefault(currentUserid, Collections.emptyMap()).containsKey(y)) {
                    productScores.merge(model ? y : xSimilar, similarityScore, Double::sum);
                }
            }
        }

        // 对候选推荐项按照总分降序排序并截取指定数量
        List<String> recommendations = productScores.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(numRecommendations)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        logger.info("为用户 {} 生成的推荐列表大小为 {}: {}", currentUserid, recommendations.size(), recommendations);

        return recommendations;
    }

    /**
     * 用户行为实体类，记录用户对某个物品的操作及评分
     */
    @Data
    public static class UserBehavior {
        private String userId;   // 用户ID
        private String itemId;   // 物品ID
        private Double score;    // 用户对该物品的评分

        /**
         * 构造方法：带评分初始化
         *
         * @param userId 用户ID
         * @param itemId 物品ID
         * @param score  评分值
         */
        public UserBehavior(String userId, String itemId, Double score) {
            this.userId = userId;
            this.itemId = itemId;
            this.score = score;
        }

        /**
         * 构造方法：默认评分为1.0
         *
         * @param userId 用户ID
         * @param itemId 物品ID
         */
        public UserBehavior(String userId, String itemId) {
            this.userId = userId;
            this.itemId = itemId;
            this.score = 1.0;
        }
    }
}