package com.arbiter.pricecollector.service.client;

import com.arbiter.core.model.PriceMessageDto;
import com.arbiter.pricecollector.service.message.KafkaProducerService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpbitWebSocketListener extends WebSocketListener {
    private final UpbitMarketSearchClient upbitMarketSearchClient;
    private final ObjectMapper objectMapper;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        log.info("업비트 WebSocket 연결 성공");

        try {
            // 모든 KRW 마켓 종목 조회
            List<String> markets = upbitMarketSearchClient.getAllKRWMarkets();

            // 구독 메시지 생성
            String subscribeMessage = objectMapper.writeValueAsString(Arrays.asList(
                    new TicketField("ARBITER-PRICE"),
                    new TypeField("ticker", markets)
            ));

            webSocket.send(subscribeMessage);
            log.info("구독 요청 완료");
        } catch (Exception e) {
            log.error("구독 요청 실패", e);
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        try {
            JsonNode data = objectMapper.readTree(text);
            log.info("가격 데이터: market={}, price={}, timestamp={}",
                    data.get("code").asText(),
                    data.get("trade_price").asText(),
                    data.get("timestamp").asText()
            );
        } catch (Exception e) {
            log.error("메시지 처리 중 오류", e);
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, okio.ByteString bytes) {
        try {
            String text = bytes.utf8();
            PriceMessageDto priceMessageDto = objectMapper.readValue(text, PriceMessageDto.class);
            kafkaProducerService.sendPrice(priceMessageDto);
        } catch (Exception e) {
            log.error("메시지 처리 중 오류", e);
        }
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        log.warn("업비트 WebSocket 연결 종료 중: code={}, reason={}", code, reason);
        webSocket.close(1000, null);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        log.warn("업비트 WebSocket 연결 종료됨: code={}, reason={}", code, reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        log.error("WebSocket 에러 발생", t);
    }

    // 웹소켓 요청 필드를 위한 내부 클래스들
    private static class TicketField {
        public String ticket;

        public TicketField(String ticket) {
            this.ticket = ticket;
        }
    }

    private static class TypeField {
        public String type;
        public List<String> codes;

        public TypeField(String type, List<String> codes) {
            this.type = type;
            this.codes = codes;
        }
    }
}