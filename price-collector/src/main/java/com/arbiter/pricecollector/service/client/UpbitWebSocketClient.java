package com.arbiter.pricecollector.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpbitWebSocketClient {
    private static final String UPBIT_WS_URL = "wss://api.upbit.com/websocket/v1";

    private final UpbitWebSocketListener upbitWebSocketListener;
    private WebSocket webSocket;

    @PostConstruct
    public void connect() {
        try {
            log.info("[UpbitWebSocketClient] connect...");
            OkHttpClient client = new OkHttpClient.Builder()
                    .pingInterval(30, TimeUnit.SECONDS)  // 30초마다 ping
                    .retryOnConnectionFailure(true)      // 연결 실패시 재시도
                    .build();

            Request request = new Request.Builder()
                    .url(UPBIT_WS_URL)
                    .build();

            webSocket = client.newWebSocket(request, upbitWebSocketListener);

            log.info("WebSocket 연결 요청 완료");
        } catch (Exception e) {
            log.error("WebSocket 연결 실패", e);
        }
    }

    @PreDestroy
    public void disconnect() {
        if (webSocket != null) {
            webSocket.close(1000, "Application shutting down");
        }
    }
}