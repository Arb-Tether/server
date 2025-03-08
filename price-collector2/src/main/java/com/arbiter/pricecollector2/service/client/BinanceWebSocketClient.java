package com.arbiter.pricecollector2.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class BinanceWebSocketClient {
    private static final String BINANCE_WS_URL = "wss://stream.binance.com:9443/ws";

    private final BinanceMarketSearchClient binanceMarketSearchClient;
    private final BinanceWebSocketListener binanceWebSocketListener;
    private final ObjectMapper objectMapper;

    private WebSocket webSocket;

    // 상위 10개 USDT 마켓 하드코딩
    private static final List<String> TOP_10_MARKETS = List.of(
            "btcusdt@ticker", "ethusdt@ticker", "xrpusdt@ticker", "solusdt@ticker",
            "adausdt@ticker", "dogeusdt@ticker", "trxusdt@ticker", "linkusdt@ticker",
            "hbarusdt@ticker", "xlmusdt@ticker"
    );

    @PostConstruct
    public void connect() {
        try {
            log.info("[BinanceWebSocketClient] Fetching USDT markets...");
//            List<String> usdtMarkets = binanceMarketSearchClient.getAllUSDTMarkets(); // USDT 마켓 리스트 가져오기
//
//            if (usdtMarkets.isEmpty()) {
//                log.warn("No USDT markets found! Skipping WebSocket connection.");
//                return;
//            }

            log.info("Connecting to Binance WebSocket...");
            OkHttpClient client = new OkHttpClient.Builder()
                    .pingInterval(30, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();

            Request request = new Request.Builder()
                    .url(BINANCE_WS_URL)
                    .build();

            webSocket = client.newWebSocket(request, binanceWebSocketListener);

            // WebSocket 연결 후 구독 요청
            sendSubscriptionRequest(TOP_10_MARKETS);

            log.info("Binance WebSocket 연결 요청 완료");
        } catch (Exception e) {
            log.error("Binance WebSocket 연결 실패", e);
        }
    }

    @PreDestroy
    public void disconnect() {
        if (webSocket != null) {
            webSocket.close(1000, "Application shutting down");
            log.info("Binance WebSocket 연결 종료");
        }
    }

    private void sendSubscriptionRequest(List<String> markets) {
        try {
            String subscribeMessage = objectMapper.writeValueAsString(
                    Map.of(
                            "method", "SUBSCRIBE",
                            "params", markets,
                            "id", 1
                    )
            );
            webSocket.send(subscribeMessage);
            log.info("상위 10개 마켓 구독 요청 완료: {}", markets);
        } catch (Exception e) {
            log.error("WebSocket 구독 요청 실패", e);
        }
    }
}
