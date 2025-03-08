package com.arbiter.pricecollector2.service.client;

import com.arbiter.core.model.PriceMessageDto;
import com.arbiter.pricecollector2.model.BinanceMessageDto;
import com.arbiter.pricecollector2.service.message.KafkaProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BinanceWebSocketListener extends WebSocketListener {
    private final ObjectMapper objectMapper;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        log.info("바이낸스 WebSocket 연결 성공");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        try {
            BinanceMessageDto priceData = objectMapper.readValue(text, BinanceMessageDto.class);

            log.info("Binance Last Price [{}]: {}", priceData.getTicker(), priceData.getPrice());

            PriceMessageDto priceMessageDto = new PriceMessageDto();
            priceMessageDto.setTicker(priceData.getTicker());
            priceMessageDto.setTimestamp(priceData.getTimestamp());
            priceMessageDto.setPrice(priceData.getPrice());
            priceMessageDto.setMarket("BINANCE");

            kafkaProducerService.sendPrice(priceMessageDto);
        } catch (Exception e) {
            log.error("메시지 처리 중 오류", e);
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, okio.ByteString bytes) {
        try {
            String text = bytes.utf8();

        } catch (Exception e) {
            log.error("메시지 처리 중 오류", e);
        }
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        log.warn("바이낸스 WebSocket 연결 종료 중: code={}, reason={}", code, reason);
        webSocket.close(1000, null);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        log.warn("바이낸스 WebSocket 연결 종료됨: code={}, reason={}", code, reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        log.error("WebSocket 에러 발생", t);
    }
}
