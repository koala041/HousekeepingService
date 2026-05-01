package com.algorithm.recommend;

import com.utils.FileUtil;
import lombok.Data;
import org.springframework.stereotype.Component;
import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomForest;
import weka.core.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 推荐工厂类
 *
 * 功能概述：
 * 1. 提供多种推荐算法的实现，包括协同过滤（用户-物品矩阵、物品-用户矩阵）和基于机器学习的推荐。
 * 2. 支持模型训练、保存、加载和缓存管理。
 * 3. 实现静态工厂方法，方便外部调用构建不同类型的推荐器。
 *
 * 核心组件：
 * - ModelStorage：负责模型的持久化与缓存管理。
 * - UserItemMatrixRecommender：基于用户-物品矩阵的协同过滤推荐器（用协）。
 * - ItemUserMatrixRecommender：基于物品-用户矩阵的协同过滤推荐器（物协）。
 * - ModelLoader：用于加载训练好的模型并进行混合推荐。
 * - AlgorithmTrainBuilder：用于训练新的推荐模型。
 */
@Component
public class RecommendAlgorithmFactory {

    private static final String MODEL_BASE_DIR = "algorithm/recommend/models/";

    private static final Map<String, Object> MODEL_CACHE = new ConcurrentHashMap<>();

    private static final Map<Long, Map<Long, Double>> userItemMatrix = new ConcurrentHashMap<>();

    private static final Map<Long, Map<Long, Double>> itemUserMatrix = new ConcurrentHashMap<>();

    public static class ModelStorage {

        public static void saveModel(String modelName, Object model) throws Exception {
            if (!(model instanceof Serializable)) {
                throw new IllegalArgumentException("模型必须实现Serializable接口");
            }

            String filePath = getModelFilePath(modelName);
            File modelFile = new File(filePath);

            File parentDir = modelFile.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(modelFile))) {
                oos.writeObject(model);
                MODEL_CACHE.put(modelName, model);
                System.out.println("模型已保存: " + filePath);
            }
        }

        public static Object loadModel(String modelName) throws Exception {
            if (MODEL_CACHE.containsKey(modelName)) {
                return MODEL_CACHE.get(modelName);
            }

            String filePath = getModelFilePath(modelName);
            File modelFile = new File(filePath);

            if (!modelFile.exists()) {
                throw new FileNotFoundException("模型文件不存在: " + filePath);
            }

            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(modelFile))) {
                Object model = ois.readObject();
                MODEL_CACHE.put(modelName, model);
                return model;
            }
        }

        public static boolean hasModel(String modelName) {
            if (MODEL_CACHE.containsKey(modelName)) {
                return true;
            }

            String filePath = getModelFilePath(modelName);
            return new File(filePath).exists();
        }

        public static void clearCache() {
            MODEL_CACHE.clear();
        }

        private static String getModelFilePath(String modelName) {
            if (!modelName.endsWith(".ser") && !modelName.endsWith(".model")) {
                modelName = modelName + ".model";
            }

            String projectRoot = FileUtil.getClassPathStatic();
            String modelPath = projectRoot + "/" + MODEL_BASE_DIR + modelName;
            return modelPath;
        }
    }

    public static class UserItemMatrixRecommender {
        protected Map<Long, Map<Long, Double>> matrix;
        protected List<Long> allItemIds;
        protected List<Long> allUserIds;

        public UserItemMatrixRecommender(Map<Long, Map<Long, Double>> matrix) {
            this.matrix = matrix;
            this.allItemIds = extractAllItemIds(matrix);
            this.allUserIds = new ArrayList<>(matrix.keySet());
        }

        public List<Map<String, Object>> recommendItemsWithDetails(Long userId, int numRecommendations) {
            if (!matrix.containsKey(userId)) {
                return new ArrayList<>();
            }

            Map<Long, Double> userInteractions = matrix.get(userId);
            Set<Long> interactedItems = userInteractions.keySet();

            Map<Long, Double> userSimilarities = calculateUserSimilarities(userId);
            Map<Long, Double> itemScores = new HashMap<>();

            for (Map.Entry<Long, Double> entry : userSimilarities.entrySet()) {
                Long similarUserId = entry.getKey();
                double similarity = entry.getValue();

                Map<Long, Double> similarUserItems = matrix.get(similarUserId);
                if (similarUserItems == null) continue;

                for (Map.Entry<Long, Double> itemEntry : similarUserItems.entrySet()) {
                    Long itemId = itemEntry.getKey();
                    double itemRating = itemEntry.getValue();

                    if (interactedItems.contains(itemId)) continue;

                    double score = itemRating * similarity;
                    itemScores.put(itemId,
                            itemScores.getOrDefault(itemId, 0.0) + score);
                }
            }

            List<Long> recommendedItemIds = sortAndLimit(itemScores, numRecommendations);
            List<Map<String, Object>> result = new ArrayList<>();

            for (Long itemId : recommendedItemIds) {
                Map<String, Object> itemResult = new HashMap<>();
                itemResult.put("itemId", itemId);
                itemResult.put("score", itemScores.get(itemId));
                result.add(itemResult);
            }

            return result;
        }

        public List<Long> recommendItems(Long userId, int numRecommendations) {
            if (!matrix.containsKey(userId)) {
                return new ArrayList<>();
            }

            Map<Long, Double> userInteractions = matrix.get(userId);
            Set<Long> interactedItems = userInteractions.keySet();

            Map<Long, Double> itemScores = new HashMap<>();
            Map<Long, Double> userSimilarities = calculateUserSimilarities(userId);

            for (Map.Entry<Long, Double> entry : userSimilarities.entrySet()) {
                Long similarUserId = entry.getKey();
                double similarity = entry.getValue();

                Map<Long, Double> similarUserItems = matrix.get(similarUserId);
                if (similarUserItems == null) continue;

                for (Map.Entry<Long, Double> itemEntry : similarUserItems.entrySet()) {
                    Long itemId = itemEntry.getKey();
                    if (interactedItems.contains(itemId)) continue;

                    double score = itemEntry.getValue() * similarity;
                    itemScores.put(itemId,
                            itemScores.getOrDefault(itemId, 0.0) + score);
                }
            }

            return sortAndLimit(itemScores, numRecommendations);
        }

        protected Map<Long, Double> calculateUserSimilarities(Long userId) {
            Map<Long, Double> similarities = new HashMap<>();
            Map<Long, Double> userVector = matrix.get(userId);

            if (userVector == null) return similarities;

            for (Map.Entry<Long, Map<Long, Double>> entry : matrix.entrySet()) {
                Long otherUserId = entry.getKey();
                if (otherUserId.equals(userId)) continue;

                double similarity = cosineSimilarity(userVector, entry.getValue());
                if (similarity > 0) {
                    similarities.put(otherUserId, similarity);
                }
            }

            return similarities;
        }

        protected double cosineSimilarity(Map<Long, Double> vec1, Map<Long, Double> vec2) {
            double dotProduct = 0.0;
            double norm1 = 0.0;
            double norm2 = 0.0;

            Set<Long> commonKeys = new HashSet<>(vec1.keySet());
            commonKeys.retainAll(vec2.keySet());

            for (Long key : commonKeys) {
                double v1 = vec1.get(key);
                double v2 = vec2.get(key);
                dotProduct += v1 * v2;
            }

            for (double v : vec1.values()) {
                norm1 += v * v;
            }

            for (double v : vec2.values()) {
                norm2 += v * v;
            }

            if (norm1 == 0 || norm2 == 0) return 0.0;

            return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
        }

        protected List<Long> sortAndLimit(Map<Long, Double> itemScores, int limit) {
            return itemScores.entrySet().stream()
                    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                    .limit(limit)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }

        protected List<Long> extractAllItemIds(Map<Long, Map<Long, Double>> matrix) {
            Set<Long> itemIdSet = new HashSet<>();
            for (Map<Long, Double> itemMap : matrix.values()) {
                itemIdSet.addAll(itemMap.keySet());
            }
            return new ArrayList<>(itemIdSet);
        }

        protected Set<Long> getUserInteractedItems(Long userId) {
            if (matrix.containsKey(userId)) {
                return matrix.get(userId).keySet();
            }
            return new HashSet<>();
        }

        public void printCollectionMatrix() {
            System.out.println("\n========================================");
            System.out.println("           收藏矩阵");
            System.out.println("========================================");

            List<Long> userIds = new ArrayList<>(matrix.keySet());
            Collections.sort(userIds);

            List<Long> itemIds = extractAllItemIds(matrix);
            Collections.sort(itemIds);

            System.out.print("用户\\物品ID\t");
            for (Long itemId : itemIds) {
                System.out.print(itemId + "\t");
            }
            System.out.println();

            for (Long userId : userIds) {
                System.out.print("用户" + userId + "\t\t");
                Map<Long, Double> userItems = matrix.get(userId);
                for (Long itemId : itemIds) {
                    double score = userItems != null && userItems.containsKey(itemId)
                            ? userItems.get(itemId) : 0.0;
                    System.out.print(score + "\t");
                }
                System.out.println();
            }
            System.out.println("========================================\n");
        }

        public void printSimilarityMatrix() {
            System.out.println("\n========================================");
            System.out.println("           相似度矩阵");
            System.out.println("========================================");

            List<Long> userIds = new ArrayList<>(matrix.keySet());
            Collections.sort(userIds);

            System.out.print("用户\t");
            for (Long userId : userIds) {
                System.out.print(userId + "\t");
            }
            System.out.println();

            for (Long userId1 : userIds) {
                System.out.print(userId1 + "\t");
                Map<Long, Double> userVector1 = matrix.get(userId1);

                for (Long userId2 : userIds) {
                    if (userId1.equals(userId2)) {
                        System.out.print("1.0\t");
                    } else {
                        Map<Long, Double> userVector2 = matrix.get(userId2);
                        double similarity = cosineSimilarity(userVector1, userVector2);
                        System.out.print(String.format("%.1f\t", similarity));
                    }
                }
                System.out.println();
            }
            System.out.println("========================================\n");
        }

        public void printDetailedCosineSimilarity(Long userId) {
            System.out.println("\n========================================");
            System.out.println("           余弦相似度详情");
            System.out.println("========================================");

            if (!matrix.containsKey(userId)) {
                System.out.println("用户 " + userId + " 不存在于矩阵中");
                System.out.println("========================================\n");
                return;
            }

            Map<Long, Double> userVector = matrix.get(userId);
            Map<Long, Double> similarities = calculateUserSimilarities(userId);

            for (Map.Entry<Long, Double> entry : similarities.entrySet()) {
                Long otherUserId = entry.getKey();
                double similarity = entry.getValue();
                System.out.println("用户" + userId + "与用户" + otherUserId + "的相似度为：" + similarity);
            }

            System.out.println("\n符合条件的推荐用户：");
            for (Map.Entry<Long, Double> entry : similarities.entrySet()) {
                Long otherUserId = entry.getKey();
                double similarity = entry.getValue();
                if (similarity >= 0.5) {
                    System.out.println("用户" + userId + "与用户" + otherUserId + "的相似度为：" + similarity + "（符合推荐条件）");
                } else {
                    System.out.println("用户" + userId + "与用户" + otherUserId + "的相似度为：" + similarity + "（低于0.5阈值，不纳入推荐）");
                }
            }

            System.out.println("========================================\n");
        }
    }

    public static class ItemUserMatrixRecommender {
        protected Map<Long, Map<Long, Double>> matrix;
        protected List<Long> allItemIds;
        protected List<Long> allUserIds;

        public ItemUserMatrixRecommender(Map<Long, Map<Long, Double>> matrix) {
            this.matrix = matrix;
            this.allItemIds = extractAllItemIds(matrix);
            this.allUserIds = extractAllUserIds(matrix);
        }

        public List<Long> recommendItems(Long userId, int numRecommendations) {
            Set<Long> userInteractedItems = getUserInteractedItems(userId);

            if (userInteractedItems.isEmpty()) {
                return new ArrayList<>();
            }

            Map<Long, Double> itemScores = new HashMap<>();

            Map<Long, Map<Long, Double>> itemSimilarities = calculateItemSimilarities();

            for (Long interactedItemId : userInteractedItems) {
                Map<Long, Double> similarItems = itemSimilarities.get(interactedItemId);
                if (similarItems == null) continue;

                double userRating = getItemUserRating(interactedItemId, userId);

                for (Map.Entry<Long, Double> entry : similarItems.entrySet()) {
                    Long candidateItemId = entry.getKey();
                    double similarity = entry.getValue();

                    if (userInteractedItems.contains(candidateItemId)) continue;

                    double score = userRating * similarity;
                    itemScores.put(candidateItemId,
                            itemScores.getOrDefault(candidateItemId, 0.0) + score);
                }
            }

            return sortAndLimit(itemScores, numRecommendations);
        }

        private Map<Long, Map<Long, Double>> calculateItemSimilarities() {
            Map<Long, Map<Long, Double>> similarities = new HashMap<>();

            List<Long> itemIds = new ArrayList<>(matrix.keySet());
            int n = itemIds.size();

            for (int i = 0; i < n; i++) {
                Long item1 = itemIds.get(i);
                Map<Long, Double> item1Users = matrix.get(item1);
                if (item1Users == null || item1Users.isEmpty()) continue;

                for (int j = i + 1; j < n; j++) {
                    Long item2 = itemIds.get(j);
                    Map<Long, Double> item2Users = matrix.get(item2);
                    if (item2Users == null || item2Users.isEmpty()) continue;

                    double similarity = calculateItemCosineSimilarity(item1Users, item2Users);

                    if (similarity > 0) {
                        similarities.computeIfAbsent(item1, k -> new HashMap<>())
                                .put(item2, similarity);
                        similarities.computeIfAbsent(item2, k -> new HashMap<>())
                                .put(item1, similarity);
                    }
                }
            }

            return similarities;
        }

        private double calculateItemCosineSimilarity(Map<Long, Double> item1Users, Map<Long, Double> item2Users) {
            double dotProduct = 0.0;
            double norm1 = 0.0;
            double norm2 = 0.0;

            Set<Long> commonUsers = new HashSet<>(item1Users.keySet());
            commonUsers.retainAll(item2Users.keySet());

            for (Long userId : commonUsers) {
                double v1 = item1Users.get(userId);
                double v2 = item2Users.get(userId);
                dotProduct += v1 * v2;
            }

            for (double v : item1Users.values()) {
                norm1 += v * v;
            }

            for (double v : item2Users.values()) {
                norm2 += v * v;
            }

            if (norm1 == 0 || norm2 == 0) return 0.0;

            return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
        }

        private double getItemUserRating(Long itemId, Long userId) {
            if (matrix.containsKey(itemId) && matrix.get(itemId).containsKey(userId)) {
                return matrix.get(itemId).get(userId);
            }
            return 0.0;
        }

        private Set<Long> getUserInteractedItems(Long userId) {
            Set<Long> interactedItems = new HashSet<>();

            for (Map.Entry<Long, Map<Long, Double>> entry : matrix.entrySet()) {
                Long itemId = entry.getKey();
                Map<Long, Double> users = entry.getValue();

                if (users.containsKey(userId)) {
                    interactedItems.add(itemId);
                }
            }

            return interactedItems;
        }

        private List<Long> extractAllItemIds(Map<Long, Map<Long, Double>> matrix) {
            return new ArrayList<>(matrix.keySet());
        }

        private List<Long> extractAllUserIds(Map<Long, Map<Long, Double>> matrix) {
            Set<Long> userIds = new HashSet<>();
            for (Map<Long, Double> userMap : matrix.values()) {
                userIds.addAll(userMap.keySet());
            }
            return new ArrayList<>(userIds);
        }

        private List<Long> sortAndLimit(Map<Long, Double> itemScores, int limit) {
            return itemScores.entrySet().stream()
                    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                    .limit(limit)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
    }

    public static class ModelLoader {
        private Classifier wekaModel;
        private String modelType;
        private String modelName;

        private List<Long> allItemIds;
        private Map<Long, Map<String, Object>> userFeatures;
        private Map<Long, Map<String, Object>> itemFeatures;
        private List<String> featureNames;
        private Map<Long, Set<Long>> userInteractedItems;

        public ModelLoader(String modelName) throws Exception {
            this.modelName = modelName;
            loadModel();
        }

        private void loadModel() {
            try {
                Object modelObj = ModelStorage.loadModel(modelName);

                if (modelObj instanceof Classifier) {
                    this.wekaModel = (Classifier) modelObj;

                    if (wekaModel instanceof RandomForest) {
                        modelType = "随机森林";
                    } else if (wekaModel.getClass().getName().contains("MultilayerPerceptron")) {
                        modelType = "NCF深度学习";
                    } else {
                        modelType = "Unknown";
                    }

                    try {
                        loadModelMetadata();
                    } catch (Exception e) {
                        System.out.println("模型元数据加载失败: " + e.getMessage());
                    }
                } else {
                    throw new IllegalArgumentException("加载的模型不是Weka Classifier类型");
                }
            } catch (Exception e)
            {
            }
        }

        private void loadModelMetadata() throws Exception {
            String metadataFile = modelName.replace(".model", "_metadata.ser");
            try {
                Object metadataObj = ModelStorage.loadModel(metadataFile);
                if (metadataObj instanceof Map) {
                    Map<String, Object> metadata = (Map<String, Object>) metadataObj;
                    this.allItemIds = (List<Long>) metadata.get("allItemIds");
                    this.userFeatures = (Map<Long, Map<String, Object>>) metadata.get("userFeatures");
                    this.itemFeatures = (Map<Long, Map<String, Object>>) metadata.get("itemFeatures");
                    this.featureNames = (List<String>) metadata.get("featureNames");
                    this.userInteractedItems = (Map<Long, Set<Long>>) metadata.get("userInteractedItems");

                    System.out.println("模型元数据加载成功，用户数量: " +
                            (userFeatures != null ? userFeatures.size() : 0) +
                            ", 物品数量: " + (allItemIds != null ? allItemIds.size() : 0));
                }
            } catch (Exception e) {
                System.out.println("警告: 模型元数据不存在或格式错误: " + e.getMessage());
            }
        }

        public UserItemMatrixRecommender buildUserItemMatrix(List<UserBehavior> list) {
            buildUserItemMatrixInternal(list);
            return new UserItemMatrixRecommender(userItemMatrix);
        }

        public ItemUserMatrixRecommender buildItemUserMatrix(List<UserBehavior> list) {
            buildItemUserMatrixInternal(list);
            return new ItemUserMatrixRecommender(itemUserMatrix);
        }

        public List<Long> recommendItems(Long userId, int numRecommendations) {
            return recommendItems(userId, numRecommendations, true);
        }

        public List<Long> recommendItems(Long userId, int numRecommendations, boolean useUserItemMatrix) {
            List<Long> modelRecommendations = new ArrayList<>();

            try {
                if (wekaModel != null && allItemIds != null && !allItemIds.isEmpty()) {
                    modelRecommendations = predictWithModel(userId);
                    System.out.println("模型推荐了 " + modelRecommendations.size() + " 个物品");
                }
            } catch (Exception e) {
                System.out.println("模型预测失败: " + e.getMessage());
                e.printStackTrace();
            }

            if (modelRecommendations.size() >= numRecommendations) {
                return modelRecommendations.subList(0, numRecommendations);
            }

            List<Long> cfRecommendations = new ArrayList<>();
            try {
                if (useUserItemMatrix) {
                    UserItemMatrixRecommender recommender = new UserItemMatrixRecommender(userItemMatrix);
                    cfRecommendations = recommender.recommendItems(userId, numRecommendations - modelRecommendations.size());
                    System.out.println("用协补充了 " + cfRecommendations.size() + " 个物品");
                } else {
                    ItemUserMatrixRecommender recommender = new ItemUserMatrixRecommender(itemUserMatrix);
                    cfRecommendations = recommender.recommendItems(userId, numRecommendations - modelRecommendations.size());
                    System.out.println("物协补充了 " + cfRecommendations.size() + " 个物品");
                }
            } catch (Exception e) {
                System.out.println("协同过滤推荐失败: " + e.getMessage());
            }

            Set<Long> finalRecommendations = new LinkedHashSet<>(modelRecommendations);
            for (Long itemId : cfRecommendations) {
                if (finalRecommendations.size() < numRecommendations) {
                    finalRecommendations.add(itemId);
                } else {
                    break;
                }
            }

            System.out.println("最终推荐了 " + finalRecommendations.size() + " 个物品");
            return new ArrayList<>(finalRecommendations);
        }

        private List<Long> predictWithModel(Long userId) {
            if (wekaModel == null || allItemIds == null || allItemIds.isEmpty()) {
                System.out.println("模型或物品列表为空，无法进行预测");
                return new ArrayList<>();
            }

            Set<Long> userInteractedSet = getUserInteractedItems(userId);
            System.out.println("用户 " + userId + " 已交互的物品数量: " + userInteractedSet.size());

            Map<Long, Double> itemScores = new HashMap<>();

            try {
                Map<String, Object> userFeature = userFeatures != null ?
                        userFeatures.get(userId) : new HashMap<>();

                if (userFeature.isEmpty()) {
                    System.out.println("用户 " + userId + " 的特征数据为空");
                }

                int predictedCount = 0;
                for (Long itemId : allItemIds) {
                    if (userInteractedSet.contains(itemId)) {
                        continue;
                    }

                    try {
                        Map<String, Object> itemFeature = itemFeatures != null ?
                                itemFeatures.get(itemId) : new HashMap<>();

                        Instance instance = createPredictionInstance(userId, itemId, userFeature, itemFeature);
                        if (instance != null) {
                            double prediction = wekaModel.classifyInstance(instance);
                            itemScores.put(itemId, prediction);
                            predictedCount++;
                        }
                    } catch (Exception e) {
                        System.out.println("物品 " + itemId + " 预测失败: " + e.getMessage());
                    }
                }

                System.out.println("模型为 " + userId + " 预测了 " + predictedCount + " 个物品的评分");
            } catch (Exception e) {
                System.out.println("模型预测过程失败: " + e.getMessage());
                e.printStackTrace();
            }

            return sortAndLimit(itemScores, allItemIds.size());
        }

        private Set<Long> getUserInteractedItems(Long userId) {
            Set<Long> interactedItems = new HashSet<>();

            if (userInteractedItems != null && userInteractedItems.containsKey(userId)) {
                interactedItems.addAll(userInteractedItems.get(userId));
            }

            if (userItemMatrix.containsKey(userId)) {
                interactedItems.addAll(userItemMatrix.get(userId).keySet());
            }

            return interactedItems;
        }

        private Instance createPredictionInstance(Long userId, Long itemId,
                                                  Map<String, Object> userFeature,
                                                  Map<String, Object> itemFeature) {
            try {
                Instances datasetTemplate = null;
                try {
                    Field datasetField = wekaModel.getClass().getDeclaredField("m_Train");
                    datasetField.setAccessible(true);
                    datasetTemplate = (Instances) datasetField.get(wekaModel);
                } catch (Exception e) {
                    datasetTemplate = createDefaultDataset();
                }

                if (datasetTemplate == null) {
                    System.out.println("无法创建数据集模板");
                    return null;
                }

                double[] values = new double[datasetTemplate.numAttributes()];

                for (int i = 0; i < datasetTemplate.numAttributes() - 1; i++) {
                    Attribute attr = datasetTemplate.attribute(i);
                    String attrName = attr.name();

                    if ("userId".equals(attrName)) {
                        values[i] = userId;
                    } else if ("itemId".equals(attrName)) {
                        values[i] = itemId;
                    } else if (userFeature.containsKey(attrName)) {
                        Object value = userFeature.get(attrName);
                        setAttributeValue(attr, values, i, value);
                    } else if (itemFeature.containsKey(attrName)) {
                        Object value = itemFeature.get(attrName);
                        setAttributeValue(attr, values, i, value);
                    } else {
                        values[i] = Utils.missingValue();
                    }
                }

                values[datasetTemplate.numAttributes() - 1] = Utils.missingValue();

                Instance instance = new DenseInstance(1.0, values);
                instance.setDataset(datasetTemplate);

                return instance;
            } catch (Exception e) {
                System.out.println("创建预测实例失败: " + e.getMessage());
                return null;
            }
        }

        private void setAttributeValue(Attribute attr, double[] values, int index, Object value) {
            if (value == null) {
                values[index] = Utils.missingValue();
            } else if (attr.isNumeric()) {
                if (value instanceof Number) {
                    values[index] = ((Number) value).doubleValue();
                } else {
                    try {
                        values[index] = Double.parseDouble(value.toString());
                    } catch (NumberFormatException e) {
                        values[index] = Utils.missingValue();
                    }
                }
            } else {
                String strValue = String.valueOf(value);
                int valueIndex = attr.indexOfValue(strValue);
                if (valueIndex >= 0) {
                    values[index] = valueIndex;
                } else {
                    values[index] = Utils.missingValue();
                }
            }
        }

        private Instances createDefaultDataset() {
            ArrayList<Attribute> attrs = new ArrayList<>();
            attrs.add(new Attribute("userId"));
            attrs.add(new Attribute("itemId"));

            if (featureNames != null) {
                for (String featName : featureNames) {
                    if (!featName.equals("userId") && !featName.equals("itemId")) {
                        attrs.add(new Attribute(featName));
                    }
                }
            }

            List<String> classLabels = Arrays.asList("0", "1");
            attrs.add(new Attribute("score", classLabels));

            Instances dataset = new Instances("temp", attrs, 0);
            dataset.setClassIndex(dataset.numAttributes() - 1);
            return dataset;
        }

        private List<Long> sortAndLimit(Map<Long, Double> itemScores, int limit) {
            return itemScores.entrySet().stream()
                    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                    .limit(limit)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
    }

    public static class AlgorithmTrainBuilder {
        private String algorithmType;
        private String className;

        private List<Long> allItemIds;
        private Map<Long, Map<String, Object>> userFeatures;
        private Map<Long, Map<String, Object>> itemFeatures;
        private List<String> featureNames;
        private Map<Long, Set<Long>> userInteractedItems;

        public AlgorithmTrainBuilder(String algorithmType, String className) {
            this.algorithmType = algorithmType;
            this.className = className;
        }

        public void train(List<Map<String, Object>> userDataList,
                          List<Map<String, Object>> itemDataList,
                          List<UserBehavior> interactionList) throws Exception {

            saveMetadata(userDataList, itemDataList, interactionList);

            Instances trainingData = prepareTrainingData(userDataList, itemDataList, interactionList);

            Classifier model;

            if ("NCF深度学习".equalsIgnoreCase(algorithmType)) {
                model = trainNCFModel(trainingData);
            } else if ("随机森林".equalsIgnoreCase(algorithmType)) {
                model = trainRandomForestModel(trainingData);
            } else {
                throw new IllegalArgumentException("不支持的算法类型: " + algorithmType);
            }

            String modelName = className + ".model";
            ModelStorage.saveModel(modelName, model);

            saveModelMetadata(modelName);

            System.out.println("模型训练完成并保存为: " + modelName);
        }

        private void saveMetadata(List<Map<String, Object>> userDataList,
                                  List<Map<String, Object>> itemDataList,
                                  List<UserBehavior> interactionList) {
            Set<Long> itemIdSet = new HashSet<>();
            for (UserBehavior behavior : interactionList) {
                itemIdSet.add(behavior.getItemId());
            }
            this.allItemIds = new ArrayList<>(itemIdSet);

            this.userFeatures = new HashMap<>();
            for (Map<String, Object> user : userDataList) {
                Long userId = ((Number) user.get("userId")).longValue();
                userFeatures.put(userId, user);
            }

            this.itemFeatures = new HashMap<>();
            for (Map<String, Object> item : itemDataList) {
                Long itemId = ((Number) item.get("itemId")).longValue();
                itemFeatures.put(itemId, item);
            }

            Set<String> featureNameSet = new HashSet<>();
            if (!userDataList.isEmpty()) {
                featureNameSet.addAll(userDataList.get(0).keySet());
            }
            if (!itemDataList.isEmpty()) {
                featureNameSet.addAll(itemDataList.get(0).keySet());
            }
            this.featureNames = new ArrayList<>(featureNameSet);

            this.userInteractedItems = new HashMap<>();
            for (UserBehavior behavior : interactionList) {
                userInteractedItems
                        .computeIfAbsent(behavior.getUserId(), k -> new HashSet<>())
                        .add(behavior.getItemId());
            }

            System.out.println("元数据统计:");
            System.out.println("  用户数量: " + userFeatures.size());
            System.out.println("  物品数量: " + allItemIds.size());
            System.out.println("  特征数量: " + featureNames.size());
            System.out.println("  交互记录数: " + interactionList.size());
        }

        private void saveModelMetadata(String modelName) throws Exception {
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("allItemIds", allItemIds);
            metadata.put("userFeatures", userFeatures);
            metadata.put("itemFeatures", itemFeatures);
            metadata.put("featureNames", featureNames);
            metadata.put("userInteractedItems", userInteractedItems);

            String metadataFile = modelName.replace(".model", "_metadata.ser");
            ModelStorage.saveModel(metadataFile, metadata);

            System.out.println("模型元数据已保存: " + metadataFile);
            System.out.println("  用户交互记录统计:");
            for (Map.Entry<Long, Set<Long>> entry : userInteractedItems.entrySet()) {
                System.out.println("    用户 " + entry.getKey() + ": " + entry.getValue().size() + " 个物品");
            }
        }

        private Instances prepareTrainingData(List<Map<String, Object>> userDataList,
                                              List<Map<String, Object>> itemDataList,
                                              List<UserBehavior> interactionList) {

            Map<Long, Map<String, Object>> userMap = new HashMap<>();
            Map<Long, Map<String, Object>> itemMap = new HashMap<>();

            userDataList.forEach(user -> {
                Long userId = ((Number) user.get("userId")).longValue();
                userMap.put(userId, user);
            });

            itemDataList.forEach(item -> {
                Long itemId = ((Number) item.get("itemId")).longValue();
                itemMap.put(itemId, item);
            });

            Set<String> allFeatureNames = new HashSet<>();
            allFeatureNames.add("userId");
            allFeatureNames.add("itemId");

            if (!userDataList.isEmpty()) {
                Map<String, Object> sampleUser = userDataList.get(0);
                for (String key : sampleUser.keySet()) {
                    if (!"userId".equals(key)) {
                        allFeatureNames.add(key);
                    }
                }
            }

            if (!itemDataList.isEmpty()) {
                Map<String, Object> sampleItem = itemDataList.get(0);
                for (String key : sampleItem.keySet()) {
                    if (!"itemId".equals(key)) {
                        allFeatureNames.add(key);
                    }
                }
            }

            ArrayList<Attribute> attributes = new ArrayList<>();

            for (String featureName : allFeatureNames) {
                if ("userId".equals(featureName) || "itemId".equals(featureName)) {
                    attributes.add(new Attribute(featureName));
                } else {
                    Object sampleValue = findSampleValue(featureName, userMap, itemMap);

                    if (sampleValue instanceof Number) {
                        attributes.add(new Attribute(featureName));
                    } else {
                        Set<String> values = collectAllValues(featureName, userMap, itemMap);
                        if (values.isEmpty()) {
                            attributes.add(new Attribute(featureName));
                        } else {
                            ArrayList<String> nominalValues = new ArrayList<>(values);
                            attributes.add(new Attribute(featureName, nominalValues));
                        }
                    }
                }
            }

            attributes.add(new Attribute("score"));

            Instances dataset = new Instances("RecommendationDataset", attributes, interactionList.size());
            dataset.setClassIndex(dataset.numAttributes() - 1);

            for (UserBehavior behavior : interactionList) {
                double[] values = new double[dataset.numAttributes()];

                Long userId = behavior.getUserId();
                Long itemId = behavior.getItemId();
                Map<String, Object> userData = userMap.get(userId);
                Map<String, Object> itemData = itemMap.get(itemId);

                int attrIndex = 0;
                for (String featureName : allFeatureNames) {
                    Attribute attr = attributes.get(attrIndex);

                    if ("userId".equals(featureName)) {
                        values[attrIndex] = userId;
                    } else if ("itemId".equals(featureName)) {
                        values[attrIndex] = itemId;
                    } else {
                        Object value = null;

                        if (userData != null && userData.containsKey(featureName)) {
                            value = userData.get(featureName);
                        } else if (itemData != null && itemData.containsKey(featureName)) {
                            value = itemData.get(featureName);
                        }

                        if (value != null) {
                            if (attr.isNumeric()) {
                                values[attrIndex] = ((Number) value).doubleValue();
                            } else {
                                String strValue = String.valueOf(value);
                                int valueIndex = attr.indexOfValue(strValue);
                                if (valueIndex >= 0) {
                                    values[attrIndex] = valueIndex;
                                } else {
                                    values[attrIndex] = Utils.missingValue();
                                }
                            }
                        } else {
                            values[attrIndex] = Utils.missingValue();
                        }
                    }
                    attrIndex++;
                }

                values[attrIndex] = behavior.getScore();

                dataset.add(new DenseInstance(1.0, values));
            }

            System.out.println("训练数据集创建完成: " + dataset.numInstances() + " 条记录");
            return dataset;
        }

        private Object findSampleValue(String featureName,
                                       Map<Long, Map<String, Object>> userMap,
                                       Map<Long, Map<String, Object>> itemMap) {
            for (Map<String, Object> userData : userMap.values()) {
                if (userData.containsKey(featureName)) {
                    return userData.get(featureName);
                }
            }

            for (Map<String, Object> itemData : itemMap.values()) {
                if (itemData.containsKey(featureName)) {
                    return itemData.get(featureName);
                }
            }

            return null;
        }

        private Set<String> collectAllValues(String featureName,
                                             Map<Long, Map<String, Object>> userMap,
                                             Map<Long, Map<String, Object>> itemMap) {
            Set<String> values = new HashSet<>();

            for (Map<String, Object> userData : userMap.values()) {
                if (userData.containsKey(featureName)) {
                    Object value = userData.get(featureName);
                    if (value != null) {
                        values.add(String.valueOf(value));
                    }
                }
            }

            for (Map<String, Object> itemData : itemMap.values()) {
                if (itemData.containsKey(featureName)) {
                    Object value = itemData.get(featureName);
                    if (value != null) {
                        values.add(String.valueOf(value));
                    }
                }
            }

            return values;
        }

        private Classifier trainNCFModel(Instances trainingData) throws Exception {
            weka.classifiers.functions.MultilayerPerceptron mlp =
                    new weka.classifiers.functions.MultilayerPerceptron();

            mlp.setLearningRate(0.3);
            mlp.setMomentum(0.2);
            mlp.setTrainingTime(500);
            mlp.setHiddenLayers("50,25");

            mlp.buildClassifier(trainingData);

            return mlp;
        }

        private Classifier trainRandomForestModel(Instances trainingData) throws Exception {
            RandomForest rf = new RandomForest();

            rf.setNumIterations(100);
            rf.setMaxDepth(0);

            rf.buildClassifier(trainingData);

            return rf;
        }
    }

    private static void buildUserItemMatrixInternal(List<UserBehavior> list) {
        userItemMatrix.clear();
        for (UserBehavior behavior : list) {
            userItemMatrix
                    .computeIfAbsent(behavior.getUserId(), k -> new HashMap<>())
                    .put(behavior.getItemId(), behavior.getScore());
        }
    }

    private static void buildItemUserMatrixInternal(List<UserBehavior> list) {
        itemUserMatrix.clear();
        for (UserBehavior behavior : list) {
            itemUserMatrix
                    .computeIfAbsent(behavior.getItemId(), k -> new HashMap<>())
                    .put(behavior.getUserId(), behavior.getScore());
        }
    }

    public static UserItemMatrixRecommender buildUserItemMatrix(List<UserBehavior> list) {
        buildUserItemMatrixInternal(list);
        return new UserItemMatrixRecommender(userItemMatrix);
    }

    public static ItemUserMatrixRecommender buildItemUserMatrix(List<UserBehavior> list) {
        buildItemUserMatrixInternal(list);
        return new ItemUserMatrixRecommender(itemUserMatrix);
    }

    public static AlgorithmTrainBuilder buildAlgorithm(String algorithmType, String className) {
        return new AlgorithmTrainBuilder(algorithmType, className);
    }

    public static ModelLoader loadModel(String modelName) throws Exception {
        return new ModelLoader(modelName);
    }

    public static boolean hasModel(String modelName) {
        return ModelStorage.hasModel(modelName);
    }

    public static List<String> listModels() {
        List<String> models = new ArrayList<>();
        try {
            String projectRoot = System.getProperty("user.dir");
            String modelDirPath = projectRoot + "/" + MODEL_BASE_DIR;
            File modelDir = new File(modelDirPath);

            if (modelDir.exists() && modelDir.isDirectory()) {
                File[] files = modelDir.listFiles((dir, name) ->
                        name.endsWith(".model") || name.endsWith(".ser"));

                if (files != null) {
                    for (File file : files) {
                        models.add(file.getName());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("获取模型列表失败: " + e.getMessage());
        }

        return models;
    }

    public static void clearCache() {
        userItemMatrix.clear();
        itemUserMatrix.clear();
        MODEL_CACHE.clear();
    }

    @Data
    public static class UserBehavior {
        private Long userId;
        private Long itemId;
        private Double score;

        public UserBehavior(Long userId, Long itemId, Double score) {
            this.userId = userId;
            this.itemId = itemId;
            this.score = score;
        }

        public UserBehavior(Long userId, Long itemId) {
            this.userId = userId;
            this.itemId = itemId;
            this.score = 1.0;
        }
    }
}