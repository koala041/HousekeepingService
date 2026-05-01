package com.ws;

import com.alibaba.fastjson.JSONObject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class WebSocketManager {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /**
     * 通用消息传递WebSocket端点
     * 端点地址: ws://127.0.0.1:8080/ws?user_id={userId}&to_id={toId}
     * 功能: 实现用户间的消息传递
     */
    @ServerEndpoint(value = "/ws")
    @Component
    public static class GeneralWebSocket {
        private static ConcurrentHashMap<String, GeneralWebSocket> webSocketMap = new ConcurrentHashMap<>();
        private String fromUserId;
        private String toUserId;
        private Session session;

        public static void sendMessage(String message, String fromUserId, String toUserId) {
            if (toUserId == null || toUserId.isEmpty()) {
                for (Map.Entry<String, GeneralWebSocket> entry : webSocketMap.entrySet()) {
                    try {
                        if (!entry.getKey().equals(fromUserId)) {
                            entry.getValue().session.getBasicRemote().sendText(message);
                            System.out.println("【websocket消息】发送消息成功,用户id=" + entry.getKey() + ",消息内容：" + message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                GeneralWebSocket webSocket = webSocketMap.get(toUserId);
                if (webSocket != null) {
                    try {
                        webSocket.session.getBasicRemote().sendText(message);
                        System.out.println("【websocket消息】发送消息成功,用户id=" + toUserId + ",消息内容：" + message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @OnOpen
        public void onOpen(Session session) {
            Map<String, String> param = parseQueryString(session.getQueryString());
            this.fromUserId = param.get("user_id");
            this.toUserId = param.get("to_id");
            this.session = session;
            webSocketMap.put(fromUserId, this);
            System.out.println("【websocket消息】有新的连接,连接id=" + fromUserId + ":" + this);
        }

        @OnClose
        public void onClose(Session session) {
            Map<String, String> param = parseQueryString(session.getQueryString());
            String fromUserId = param.get("user_id");
            String toUserId = param.get("to_id");
            webSocketMap.remove(fromUserId);
            System.out.println("【websocket消息】连接断开:" + fromUserId);
        }

        @OnError
        public void onError(Session session, Throwable error) {
            System.out.println("【websocket消息】WebSocket发生错误，错误信息为：" + error.getMessage());
            error.printStackTrace();
        }

        @OnMessage
        public void onMessage(String message) {
            System.out.println("【websocket实例】" + this);
            if ("ping".equals(message)) {
                sendMessage("pong", fromUserId, toUserId);
            } else {
                System.out.println("【websocket消息】收到客户端发来的消息:" + message);
                sendMessage(message, fromUserId, toUserId);
            }
        }

        private Map<String, String> parseQueryString(String queryString) {
            Map<String, String> query_pairs = new HashMap<>();
            String[] pairs = queryString.split("&");
            try {
                for (String pair : pairs) {
                    int idx = pair.indexOf("=");
                    String key = (idx > 0) ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
                    String value = (idx > 0 && pair.length() > idx + 1) ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
                    query_pairs.put(key, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return query_pairs;
        }
    }
}
