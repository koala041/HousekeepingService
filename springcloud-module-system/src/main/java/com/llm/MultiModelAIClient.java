package com.llm;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.utils.*;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 多模型 AI 流式客户端工具类
 * 支持文本 + 多文件上传，支持流式/非流式输出
 * <p>
 * 该工具类提供了统一的接口来访问多种AI模型服务（如百度千帆、DeepSeek、火山引擎、通义千问等），
 * 支持同步和异步调用模式，能够处理包含文本消息和多个附件文件的复杂请求。
 * <p>
 * 主要特性：
 * - 支持多模型适配：通过适配器模式兼容不同AI服务商的API协议
 * - 文件处理能力：自动识别并处理图片、PDF、文本等多种文件类型
 * - 流式传输支持：提供SSE(Server-Sent Events)方式的流式响应处理
 * - 字符编码检测：智能检测文本文件的字符编码格式
 * - 统一错误处理：标准化异常处理和错误信息返回
 */
@Slf4j
public final class MultiModelAIClient {

    // JSON对象映射器，用于JSON序列化和反序列化
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // 线程池服务，用于异步任务执行
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    // 默认HTTP请求超时时间（毫秒）
    private static final int DEFAULT_TIMEOUT = 60000;

    // SSE连接超时时间（毫秒）
    private static final long SSE_TIMEOUT = 180000L;

    /**
     * 私有构造函数，防止实例化
     * 这是一个工具类，不应被实例化
     */
    private MultiModelAIClient() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // ==================== 内部请求类 ====================

    /**
     * 聊天请求数据类
     * 封装了向AI模型发送请求所需的所有参数
     */
    @Data
    public static class ChatRequest {
        /**
         * 模型名称，用于指定使用的AI服务
         */
        private String model;

        /**
         * 用户输入的消息内容
         */
        private String message;

        /**
         * 逗号分隔的文件名列表
         */
        private String fileNames;

        /**
         * 是否启用流式输出模式
         */
        private Boolean stream = false;

        /**
         * 生成文本的温度参数，控制随机性
         */
        private Double temperature = 0.7;

        /**
         * 最大生成令牌数限制
         */
        private Integer maxTokens = 2000;

        /**
         * 额外的参数映射，用于传递特定于模型的配置
         */
        private Map<String, Object> extraParams;

        /**
         * 文件对象列表（临时字段，不会被序列化）
         */
        private transient List<File> files;

        /**
         * 系统提示语，用于设置AI模型中的系统提示信息
         */
        private String systemPrompt;

        /**
         * 对话历史记录列表
         */
        private List<Message> history;

        /**
         * 获取文件列表，如果尚未加载则从文件名解析
         *
         * @return 文件列表，可能为空但不会为null
         */
        public List<File> getFiles() {
            if (files == null && fileNames != null && !fileNames.trim().isEmpty()) {
                files = new ArrayList<>();
                String[] nameArray = fileNames.split(",");
                for (String name : nameArray) {
                    String trimmedName = name.trim();
                    if (!trimmedName.isEmpty()) {
                        try {
                            File file = FileUtil.getFile(trimmedName);
                            if (file != null && file.exists()) {
                                files.add(file);
                            } else {
                                log.warn("文件不存在：{}", trimmedName);
                            }
                        } catch (Exception e) {
                            log.error("获取文件失败：{}", trimmedName, e);
                        }
                    }
                }
            }
            return files;
        }

        /**
         * 判断是否存在附加文件
         *
         * @return 如果存在至少一个文件返回true，否则返回false
         */
        public boolean hasFiles() {
            List<File> fileList = getFiles();
            return fileList != null && !fileList.isEmpty();
        }

        /**
         * 判断是否存在对话历史
         *
         * @return 如果存在至少一条历史消息返回true，否则返回false
         */
        public boolean hasHistory() {
            return history != null && !history.isEmpty();
        }

        /**
         * 获取系统提示语
         *
         * @return 系统提示语，可能为空但不会为null
         */
        public boolean hasSystemPrompt() {
            return systemPrompt != null && !systemPrompt.trim().isEmpty();
        }
    }

    /**
     * 消息数据类
     * 表示对话中的一条消息
     */
    @Data
    @AllArgsConstructor
    public static class Message {
        /**
         * 角色标识，如"user"、"assistant"、"system"
         */
        private String role;

        /**
         * 消息内容文本
         */
        private String content;
    }

    /**
 * 聊天响应数据类
 * 封装了AI模型返回的响应信息
 */
    @Data
    @AllArgsConstructor
    public static class ChatResponse {
        /**
         * 响应的唯一标识符
         */
        private String id;

        /**
         * 使用的模型名称
         */
        private String model;

        /**
         * 生成的内容文本
         */
        private String content;

        /**
         * 是否已完成生成
         */
        private Boolean finished;

        /**
         * 是否为流式传输
         */
        private Boolean streaming;

        /**
         * 时间戳（毫秒）
         */
        private Long timestamp;

        /**
         * 完成原因，如"stop"、"length"等
         */
        private String finishReason;
    }

    // ==================== 内部配置类 ====================

    /**
     * 模型配置数据类
     * 存储每个AI模型的连接配置信息
     */

    @Data
    @AllArgsConstructor
    private static class ModelConfig {
        /**
         * 模型名称标识
         */
        private String name;

        /**
         * API端点URL地址
         */
        private String url;

        /**
         * 认证用的API密钥
         */
        private String apiKey;

        /**
         * 可选的密钥（目前未使用）
         */
        private String secretKey;
    }

    // ==================== 模型配置数据 ====================
    private static final Map<String, ModelConfig> MODEL_CONFIGS = new HashMap<>();
    // 存储所有支持的AI模型的配置信息
    static {
    MODEL_CONFIGS.put("deepseek", new ModelConfig(
                "deepseek",
                "https://api.deepseek.com/v1/chat/completions",
                ConfigUtil.getConfigValue("deepseek","key"),
                null
        ));
    }

    // ==================== 适配器接口 ====================

    /**
     * 模型适配器接口
     * 定义了不同AI模型的统一访问契约
     */
    private interface ModelAdapter {
        /**
         * 构建请求URL
         *
         * @param config 模型配置
         * @param stream 是否为流式请求
         * @return 完整的请求URL字符串
         */
        String buildUrl(ModelConfig config, boolean stream);

        /**
         * 构建请求体JSON对象
         *
         * @param request 聊天请求对象
         * @return 包含所有必要参数的JSON节点
         * @throws IOException 当构建过程中发生I/O错误时抛出
         */
        ObjectNode buildRequestBody(ChatRequest request) throws IOException;

        /**
         * 解析流式响应数据
         *
         * @param json 响应的JSON节点
         * @return 提取的文本内容，可能为空字符串
         */
        String parseStreamResponse(JsonNode json);

        /**
         * 解析完整响应数据
         *
         * @param json 响应的JSON节点
         * @return 提取的文本内容，可能为空字符串
         */
        String parseResponse(JsonNode json);

        /**
         * 设置HTTP请求头
         *
         * @param conn   HTTP连接对象
         * @param config 模型配置
         * @throws Exception 当设置头信息时发生错误
         */
        void setHeaders(HttpURLConnection conn, ModelConfig config) throws Exception;

        /**
         * 判断是否为SSE格式响应
         *
         * @return true表示使用SSE格式，false表示其他格式
         */
        boolean isSSEFormat();
    }

    /**
     * 基础适配器抽象类
     * 提供通用的OpenAI格式解析和头部设置功能
     */
    private abstract static class BaseAdapter implements ModelAdapter {
        /**
         * 设置标准的HTTP请求头
         * 包括认证信息和内容类型
         *
         * @param conn   HTTP连接对象
         * @param config 模型配置
         */
        @Override
        public void setHeaders(HttpURLConnection conn, ModelConfig config) {
            conn.setRequestProperty("Authorization", "Bearer " + config.getApiKey());
            conn.setRequestProperty("Content-Type", "application/json");
        }

        /**
         * 解析流式响应中的内容片段
         * 从JSON响应中提取增量更新的内容
         *
         * @param json 响应的JSON节点
         * @return 提取的文本内容，可能为空字符串
         */
        @Override
        public String parseStreamResponse(JsonNode json) {
            JsonNode choices = json.path("choices");
            if (choices.isArray() && choices.size() > 0) {
                return choices.get(0).path("delta").path("content").asText("").replaceAll("#", "");
            }
            return "";
        }

        /**
         * 解析完整的响应数据
         * 从最终响应中提取完整的内容
         *
         * @param json 响应的JSON节点
         * @return 提取的文本内容，可能为空字符串
         */
        @Override
        public String parseResponse(JsonNode json) {
            String content = "";
            String finishReason = null;
            JsonNode choices = json.path("choices");
            if (choices.isArray() && choices.size() > 0) {
                content = choices.get(0).path("message").path("content").asText("").replaceAll("#", "");
                finishReason = choices.get(0).path("finish_reason").asText(null);
            }
            ChatResponse response = new ChatResponse(
                    json.path("id").asText(),
                    getModelName(),
                    content,
                    true,
                    false,
                    System.currentTimeMillis(),
                    finishReason
            );
            System.out.println(response);
            return content;
        }

        /**
         * 获取子类实现的模型名称
         *
         * @return 模型名称字符串
         */
        protected abstract String getModelName();

        /**
         * 判断是否为SSE格式响应
         * 默认返回true，表示使用SSE格式
         *
         * @return true表示使用SSE格式
         */
        @Override
        public boolean isSSEFormat() {
            return true;
        }

        /**
         * 构建消息数组
         * 将对话历史和当前消息组合成符合API要求的格式
         *
         * @param request 聊天请求
         * @return JSON数组节点，包含所有消息
         * @throws IOException 当处理过程中发生I/O错误
         */
        protected ArrayNode buildMessagesArray(ChatRequest request) throws IOException {
            ArrayNode messages = OBJECT_MAPPER.createArrayNode();

            if (request.hasSystemPrompt()) {
                ObjectNode systemMsg = OBJECT_MAPPER.createObjectNode();
                systemMsg.put("role", "system");
                systemMsg.put("content", request.getSystemPrompt());
                messages.add(systemMsg);
            }

            if (request.hasHistory() && request.getHistory() != null) {
                for (Message msg : request.getHistory()) {
                    ObjectNode msgNode = OBJECT_MAPPER.createObjectNode();
                    msgNode.put("role", msg.getRole());
                    msgNode.put("content", msg.getContent());
                    messages.add(msgNode);
                }
            }

            ObjectNode currentMsg = OBJECT_MAPPER.createObjectNode();
            currentMsg.put("role", "user");
            currentMsg.put("content", FileContentBuilder.buildContentWithFiles(request));
            messages.add(currentMsg);

            return messages;
        }
    }
    /**
     * DeepSeek适配器
     * 实现DeepSeek AI平台的协议适配
     */
    private static class DeepSeekAdapter extends BaseAdapter {
        /**
         * 获取模型名称
         *
         * @return 模型名称"deepseek"
         */
        @Override
        protected String getModelName() {
            return "deepseek";
        }

        /**
         * 构建请求URL
         *
         * @param config 模型配置
         * @param stream 是否为流式请求
         * @return 请求URL字符串
         */
        @Override
        public String buildUrl(ModelConfig config, boolean stream) {
            return config.getUrl();
        }

        /**
         * 构建请求体JSON对象
         *
         * @param request 聊天请求对象
         * @return 包含所有必要参数的JSON节点
         * @throws IOException 当构建过程中发生I/O错误时抛出
         */
        @Override
        public ObjectNode buildRequestBody(ChatRequest request) throws IOException {
            ObjectNode body = OBJECT_MAPPER.createObjectNode();
            body.put("model", "deepseek-chat");
            body.put("stream", request.getStream());
            body.put("temperature", request.getTemperature());
            body.put("max_tokens", request.getMaxTokens());

            ArrayNode messages = buildMessagesArray(request);
            body.set("messages", messages);

            if (request.getExtraParams() != null) {
                request.getExtraParams().forEach(body::putPOJO);
            }
            return body;
        }
    }
    // ==================== 文件内容构建工具类 ====================
    /**
     * 文件内容构建工具类
     * 负责处理各种类型的文件并将其转换为适合AI理解的格式
     */
    private static class FileContentBuilder {
        // MIME类型映射表，用于根据文件扩展名确定内容类型
        private static final Map<String, String> MIME_TYPES = new HashMap<>();

        static {
            MIME_TYPES.put("jpg", "image/jpeg");
            MIME_TYPES.put("jpeg", "image/jpeg");
            MIME_TYPES.put("png", "image/png");
            MIME_TYPES.put("gif", "image/gif");
            MIME_TYPES.put("pdf", "application/pdf");
            MIME_TYPES.put("txt", "text/plain");

            MIME_TYPES.put("mp3", "audio/mpeg");
            MIME_TYPES.put("wav", "audio/wav");
            MIME_TYPES.put("ogg", "audio/ogg");
            MIME_TYPES.put("m4a", "audio/mp4");
        }

        /**
         * 构建包含文件内容的消息
         * 将文本消息和多个文件内容组合成单一的消息字符串
         *
         * @param request 聊天请求对象，包含消息和文件信息
         * @return 组合后的消息内容字符串
         * @throws IOException 当读取文件时发生I/O错误
         */
        public static String buildContentWithFiles(ChatRequest request) throws IOException {
            StringBuilder content = new StringBuilder();

            if (request.getMessage() != null && !request.getMessage().isEmpty()) {
                content.append(request.getMessage());
            }

            List<File> files = request.getFiles();
            if (files != null && !files.isEmpty()) {
                for (File file : files) {
                    try {
                        String fileName = file.getName();
                        String contentType = guessContentType(fileName);
                        byte[] fileBytes = Files.readAllBytes(file.toPath());

                        log.info("处理文件：{}, 大小：{} bytes, 类型：{}", fileName, fileBytes.length, contentType);

                        if (contentType.startsWith("image/")) {
                            String base64 = Base64.getEncoder().encodeToString(fileBytes);
                            content.append("\n\n[图片：" + fileName + "]\n");
                            content.append("![image](data:").append(contentType)
                                    .append(";base64,").append(base64).append(")");
                        } else if (contentType.equals("application/pdf")) {
                            content.append("\n\n[PDF 文件：" + fileName)
                                    .append(", 大小：").append(formatFileSize(fileBytes.length)).append("]\n");
                        } else if (contentType.startsWith("text/")) {
                            String textContent = readTextFileWithEncodingDetection(file, fileBytes);
                            content.append("\n\n[文本文件：" + fileName + " 的内容]\n");
                            content.append(textContent);
                        } else if (contentType.startsWith("audio/")) {
                            String base64 = Base64.getEncoder().encodeToString(fileBytes);
                            content.append("\n\n[音频文件：" + fileName + "]\n");
                            content.append("![audio](data:").append(contentType)
                                    .append(";base64,").append(base64).append(")");
                        } else {
                            content.append("\n\n[附件：" + fileName)
                                    .append(", 类型：").append(contentType)
                                    .append(", 大小：").append(formatFileSize(fileBytes.length)).append("]\n");
                        }
                    } catch (Exception e) {
                        log.error("处理文件失败：{}", file.getName(), e);
                        content.append("\n\n[文件处理失败：" + file.getName() + " - " + e.getMessage() + "]");
                    }
                }
            }

            String result = content.toString();
            log.info("构建的消息内容长度：{}", result.length());
            return result;
        }

        /**
         * 使用编码检测读取文本文件
         * 自动检测文件的字符编码并正确读取内容
         *
         * @param file      要读取的文件对象
         * @param fileBytes 文件的字节数组
         * @return 文件的文本内容
         * @throws IOException 当读取文件时发生I/O错误
         */
        private static String readTextFileWithEncodingDetection(File file, byte[] fileBytes) throws IOException {
            // 检测 BOM
            if (fileBytes.length >= 3) {
                if ((fileBytes[0] & 0xFF) == 0xEF && (fileBytes[1] & 0xFF) == 0xBB && (fileBytes[2] & 0xFF) == 0xBF) {
                    return new String(fileBytes, 3, fileBytes.length - 3, StandardCharsets.UTF_8);
                }
                if ((fileBytes[0] & 0xFF) == 0xFE && (fileBytes[1] & 0xFF) == 0xFF) {
                    return new String(fileBytes, 2, fileBytes.length - 2, StandardCharsets.UTF_16BE);
                }
                if ((fileBytes[0] & 0xFF) == 0xFF && (fileBytes[1] & 0xFF) == 0xFE) {
                    return new String(fileBytes, 2, fileBytes.length - 2, StandardCharsets.UTF_16LE);
                }
            }

            // 候选编码检测
            String[] encodings = {"UTF-8", "GBK", "GB2312", "GB18030", "ISO-8859-1"};
            String bestEncoding = detectBestEncoding(fileBytes, encodings);
            try {
                return new String(fileBytes, Charset.forName(bestEncoding));
            } catch (Exception e) {
                log.warn("使用编码 {} 读取失败，回退到 UTF-8", bestEncoding, e);
                return new String(fileBytes, StandardCharsets.UTF_8);
            }
        }

        /**
         * 检测最佳编码
         * 根据候选编码列表和字节数据质量评分选择最优编码
         *
         * @param bytes     文件的原始字节数据
         * @param encodings 候选编码列表
         * @return 检测到的最佳编码名称
         */
        private static String detectBestEncoding(byte[] bytes, String[] encodings) {
            int bestScore = 0;
            String bestEncoding = "UTF-8";
            for (String encoding : encodings) {
                try {
                    String decoded = new String(bytes, Charset.forName(encoding));
                    int score = scoreTextQuality(decoded, bytes);
                    if (score > bestScore) {
                        bestScore = score;
                        bestEncoding = encoding;
                    }
                } catch (Exception ignored) {
                }
            }
            return bestEncoding;
        }

        /**
         * 评分文本质量
         * 评估解码后文本的质量，用于编码检测决策
         *
         * @param text          解码后的文本
         * @param originalBytes 原始字节数据
         * @return 质量评分，分数越高表示质量越好
         */
        private static int scoreTextQuality(String text, byte[] originalBytes) {
            int score = 0;
            // 乱码惩罚
            if (text.contains("ï¿½") || text.contains("Ã") || text.contains("Â")) {
                score -= 100;
            }
            // 中文字符加分
            int chineseCharCount = 0;
            int totalCharCount = Math.min(text.length(), 1000);
            for (int i = 0; i < totalCharCount; i++) {
                char c = text.charAt(i);
                if (c >= '\u4E00' && c <= '\u9FFF') {
                    chineseCharCount++;
                } else if (c >= '\u3000' && c <= '\u303F') {
                    chineseCharCount++;
                }
            }
            if (chineseCharCount > 10) {
                score += chineseCharCount * 2;
            }
            // 可打印字符比例加分
            int printableCount = 0;
            for (int i = 0; i < totalCharCount; i++) {
                char c = text.charAt(i);
                if (!Character.isISOControl(c)) {
                    printableCount++;
                }
            }
            score += (printableCount * 100) / Math.max(totalCharCount, 1);
            // 字节与字符比例合理性加分
            if (originalBytes.length > 0 && text.length() > 0) {
                double bytesPerChar = (double) originalBytes.length / text.length();
                if (bytesPerChar >= 1.5 && bytesPerChar <= 4.0) {
                    score += 50;
                }
            }
            return score;
        }

        /**
         * 推测内容类型
         * 根据文件扩展名推测MIME类型
         *
         * @param fileName 文件名
         * @return 对应的MIME类型字符串
         */
        private static String guessContentType(String fileName) {
            int lastDot = fileName.lastIndexOf(".");
            if (lastDot == -1) {
                return "application/octet-stream";
            }
            String ext = fileName.substring(lastDot + 1).toLowerCase();
            return MIME_TYPES.getOrDefault(ext, "application/octet-stream");
        }

        /**
         * 格式化文件大小
         * 将字节数转换为可读的KB/MB格式
         *
         * @param size 字节大小
         * @return 格式化后的大小字符串
         */
        private static String formatFileSize(long size) {
            if (size < 1024) {
                return size + " B";
            } else if (size < 1024 * 1024) {
                return String.format("%.2f KB", size / 1024.0);
            } else {
                return String.format("%.2f MB", size / (1024.0 * 1024.0));
            }
        }
    }

    // ==================== 适配器映射 ====================
    // 存储所有模型适配器实例，按模型名称索引
    private static final Map<String, ModelAdapter> ADAPTERS = new HashMap<>();

    static {
    ADAPTERS.put("deepseek", new DeepSeekAdapter());
    }

    // ==================== 公共静态 API 方法 ====================

    /**
     * 同步聊天接口
     * 发送非流式请求并等待完整响应
     *
     * @param request 聊天请求对象
     * @return AI生成的完整响应文本
     * @throws Exception 当请求处理过程中发生任何错误
     */
    public static String chat(ChatRequest request) throws Exception {
        request.setStream(false);
        return executeRequest(request);
    }

    /**
     * 直接流式聊天接口
     * 将流式响应直接写入HTTP响应输出流
     *
     * @param request  聊天请求对象
     * @param response HTTP响应对象，用于直接写入SSE数据
     */
    public static void streamChatDirect(ChatRequest request, HttpServletResponse response) throws Exception {
        request.setStream(true);

        MultiModelAIClient.ModelConfig config = validateAndGetConfig(request);
        MultiModelAIClient.ModelAdapter adapter = ADAPTERS.get(request.getModel());

        HttpURLConnection conn = null;

        try {
            conn = createConnection(adapter.buildUrl(config, true), DEFAULT_TIMEOUT);
            adapter.setHeaders(conn, config);

            String requestBody = OBJECT_MAPPER.writeValueAsString(adapter.buildRequestBody(request));
            log.info("发送给 {} 的请求体大小：{} bytes", request.getModel(), requestBody.length());

            sendRequest(conn, requestBody);

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                String errorBody = readErrorResponse(conn);
                throw new RuntimeException("HTTP 错误：" + responseCode + " - " + errorBody);
            }
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                boolean hasData = false;

                while ((line = reader.readLine()) != null) {
                    hasData = true;

                    if (adapter.isSSEFormat()) {
                        if (!line.startsWith("data: ")) {
                            continue;
                        }
                        String data = line.substring(6);
                        if ("[DONE]".equals(data)) {
                            break;
                        }

                        if (data.contains("}{")) {
                            String[] jsonParts = data.split("(?<=})");
                            for (String part : jsonParts) {
                                if (part.trim().isEmpty()) continue;
                                JsonNode jsonNode = OBJECT_MAPPER.readTree(part);
                                String content = adapter.parseStreamResponse(jsonNode);
                                if (content != null && !content.isEmpty()) {
                                    sendSseDirect(response, content);
                                }
                            }
                        } else {
                            JsonNode jsonNode = OBJECT_MAPPER.readTree(data);
                            String content = adapter.parseStreamResponse(jsonNode);
                            if (content != null && !content.isEmpty()) {
                                log.info("content:" + content);
                                sendSseDirect(response, content);
                            }
                        }
                    } else {
                        if (line.trim().isEmpty() || line.trim().startsWith("[")) {
                            continue;
                        }
                        JsonNode jsonNode = OBJECT_MAPPER.readTree(line);
                        String content = adapter.parseStreamResponse(jsonNode);
                        System.out.println("Non-SSE content:" + content);
                        if (content != null && !content.isEmpty()) {
                            sendSseDirect(response, content);
                        }
                    }
                }

                if (!hasData) {
                    sendSseDirect(response, "没有收到响应数据");
                }

                sendSseDirect(response, "[DONE]");
                response.flushBuffer();
            }

        } catch (Exception e) {
            log.error("流式处理错误", e);
            try {
                sendSseDirect(response, "错误：" + e.getMessage());
                response.flushBuffer();
            } catch (IOException ex) {
                log.error("发送错误信息失败", ex);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * 直接发送SSE数据
     * 将内容包装成SSE格式并写入响应输出流
     *
     * @param response HTTP响应对象
     * @param content  要发送的内容
     * @throws IOException 当写入输出流时发生错误
     */
    private static void sendSseDirect(HttpServletResponse response, String content) throws IOException {
        byte[] data = ("data: " + content + "\n\n").getBytes(StandardCharsets.UTF_8);
        response.getOutputStream().write(data);
        response.flushBuffer();
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 执行同步请求
     * 处理非流式请求的核心方法
     *
     * @param request 聊天请求对象
     * @return AI生成的响应文本
     * @throws Exception 当请求执行过程中发生错误
     */
    private static String executeRequest(ChatRequest request) throws Exception {
        ModelConfig config = validateAndGetConfig(request);
        ModelAdapter adapter = ADAPTERS.get(request.getModel());

        String responseBody = executeHttpRequest(adapter, config, request, false);
        JsonNode jsonNode = OBJECT_MAPPER.readTree(responseBody);
        return adapter.parseResponse(jsonNode);
    }

    /**
     * 处理流式请求
     * 通过SseEmitter发送流式响应
     *
     * @param request 聊天请求对象
     * @param emitter SseEmitter对象，用于发送事件
     * @throws Exception 当处理过程中发生错误
     */
    private static void processStreamRequest(ChatRequest request, SseEmitter emitter) throws Exception {
        ModelConfig config = validateAndGetConfig(request);
        ModelAdapter adapter = ADAPTERS.get(request.getModel());

        HttpURLConnection conn = null;
        try {
            conn = createConnection(adapter.buildUrl(config, true), DEFAULT_TIMEOUT);
            adapter.setHeaders(conn, config);

            String requestBody = OBJECT_MAPPER.writeValueAsString(adapter.buildRequestBody(request));
            log.info("发送给 {} 的请求体大小：{} bytes", request.getModel(), requestBody.length());

            sendRequest(conn, requestBody);

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                String errorBody = readErrorResponse(conn);
                throw new RuntimeException("HTTP 错误：" + responseCode + " - " + errorBody);
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                boolean hasData = false;
                while ((line = reader.readLine()) != null) {
                    hasData = true;
                    if (!processStreamLine(line, adapter, emitter)) {
                        break;
                    }
                }
                if (!hasData) {
                    emitter.send(SseEmitter.event().data("没有收到响应数据").name("error"));
                }
            }

            emitter.send(SseEmitter.event().data("[DONE]").name("end"));
            emitter.complete();

        } catch (Exception e) {
            log.error("流式处理错误", e);
            completeEmitterWithError(emitter, e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * 处理单行流式响应
     * 解析并发送单个响应片段
     *
     * @param line    响应行文本
     * @param adapter 模型适配器
     * @param emitter SSE发射器
     * @return true继续处理，false停止处理
     * @throws IOException 当处理过程中发生I/O错误
     */
    private static boolean processStreamLine(String line, ModelAdapter adapter, SseEmitter emitter) throws IOException {
        if (!line.startsWith("data: ")) {
            return true;
        }
        String data = line.substring(6);
        if ("[DONE]".equals(data)) {
            return false;
        }

        // 兼容千帆流式响应的多JSON块格式
        if (data.contains("}{")) {
            String[] jsonParts = data.split("(?<=})");
            for (String part : jsonParts) {
                if (part.trim().isEmpty()) continue;
                JsonNode jsonNode = OBJECT_MAPPER.readTree(part);
                String content = adapter.parseStreamResponse(jsonNode);
                if (content != null && !content.isEmpty()) {
                    emitter.send(SseEmitter.event().data(content).name("message"));
                }
            }
        } else {
            JsonNode jsonNode = OBJECT_MAPPER.readTree(data);
            String content = adapter.parseStreamResponse(jsonNode);
            if (content != null && !content.isEmpty()) {
                emitter.send(SseEmitter.event().data(content).name("message"));
            }
        }
        return true;
    }

    /**
     * 验证并获取模型配置
     * 检查请求中的模型是否受支持并返回其配置
     *
     * @param request 聊天请求
     * @return 对应的模型配置对象
     * @throws IllegalArgumentException 当请求的模型不受支持时
     */
    private static ModelConfig validateAndGetConfig(ChatRequest request) {
        ModelConfig config = MODEL_CONFIGS.get(request.getModel());
        if (config == null) {
            throw new IllegalArgumentException("不支持的模型：" + request.getModel() +
                    "，支持的模型：" + String.join(", ", MODEL_CONFIGS.keySet()));
        }
        return config;
    }

    /**
     * 完成SSE发射器并处理错误
     * 在发生异常时正确关闭SSE连接
     *
     * @param emitter SSE发射器
     * @param e       发生的异常
     */
    private static void completeEmitterWithError(SseEmitter emitter, Exception e) {
        try {
            emitter.send(SseEmitter.event().data("错误：" + e.getMessage()).name("error"));
        } catch (IOException ex) {
            log.error("发送错误信息失败", ex);
        }
        emitter.complete();
    }

    /**
     * 统一执行 HTTP 请求，返回响应体字符串（非流式）
     *
     * @param adapter 模型适配器
     * @param config  模型配置
     * @param request 聊天请求
     * @param stream  是否为流式请求
     * @return 响应体字符串
     * @throws Exception 当请求执行过程中发生错误
     */
    private static String executeHttpRequest(ModelAdapter adapter, ModelConfig config,
                                             ChatRequest request, boolean stream) throws Exception {
        HttpURLConnection conn = null;
        try {
            conn = createConnection(adapter.buildUrl(config, stream), DEFAULT_TIMEOUT);
            adapter.setHeaders(conn, config);

            String requestBody = OBJECT_MAPPER.writeValueAsString(adapter.buildRequestBody(request));
            sendRequest(conn, requestBody);

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                String errorBody = readErrorResponse(conn);
                throw new RuntimeException("HTTP 错误：" + responseCode + " - " + errorBody);
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * 读取HTTP错误响应
     * 从错误流中读取详细的错误信息
     *
     * @param conn HTTP连接对象
     * @return 错误响应体字符串
     * @throws IOException 当读取过程中发生I/O错误
     */
    private static String readErrorResponse(HttpURLConnection conn) throws IOException {
        InputStream errorStream = conn.getErrorStream();
        if (errorStream != null) {
            try (BufferedInputStream bis = new BufferedInputStream(errorStream)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                StringBuilder sb = new StringBuilder();
                while ((bytesRead = bis.read(buffer)) != -1) {
                    sb.append(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));
                }
                return sb.toString();
            }
        }
        return conn.getResponseMessage();
    }

    /**
     * 创建HTTP连接
     * 初始化并配置HTTP连接参数
     *
     * @param urlString 目标URL字符串
     * @param timeout   连接超时时间（毫秒）
     * @return 配置好的HttpURLConnection对象
     * @throws IOException 当创建连接时发生I/O错误
     */
    private static HttpURLConnection createConnection(String urlString, int timeout) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(0);
        return conn;
    }

    /**
     * 发送请求体数据
     * 将JSON请求体写入HTTP连接的输出流
     *
     * @param conn HTTP连接对象
     * @param body 请求体字符串
     * @throws IOException 当写入过程中发生I/O错误
     */
    private static void sendRequest(HttpURLConnection conn, String body) throws IOException {
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = body.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
            os.flush();
        }
    }

    /**
     * 获取支持的模型列表
     * 返回当前客户端支持的所有模型名称
     *
     * @return 不可修改的模型名称集合
     */
    public static Set<String> getSupportedModels() {
        return Collections.unmodifiableSet(MODEL_CONFIGS.keySet());
    }

    /**
     * 关闭资源
     * 关闭线程池等资源
     */
    public static void shutdown() {
        EXECUTOR_SERVICE.shutdown();
        try {
            if (!EXECUTOR_SERVICE.awaitTermination(5, TimeUnit.SECONDS)) {
                EXECUTOR_SERVICE.shutdownNow();
            }
        } catch (InterruptedException e) {
            EXECUTOR_SERVICE.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 更新模型配置
     * 动态添加或修改模型的配置信息
     *
     * @param modelName 模型名称
     * @param url       API端点URL
     * @param apiKey    API密钥
     * @param secretKey 密钥（可选）
     */
    public static void updateModelConfig(String modelName, String url, String apiKey, String secretKey) {
        MODEL_CONFIGS.put(modelName, new ModelConfig(modelName, url, apiKey, secretKey));
    }
}
