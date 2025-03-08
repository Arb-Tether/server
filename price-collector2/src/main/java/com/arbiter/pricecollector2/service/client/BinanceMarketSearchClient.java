package com.arbiter.pricecollector2.service.client;

import com.arbiter.pricecollector2.model.api.BinanceMarketApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BinanceMarketSearchClient {
    private static final String BINANCE_REST_URL = "https://api.binance.com/api/v3/exchangeInfo";

    private final RestTemplate restTemplate;

    public List<String> getAllUSDTMarkets() {
        try {
            log.info("바이낸스 USDT 마켓 목록 조회 시도...");
            ResponseEntity<BinanceMarketApiResponse> response = restTemplate.getForEntity(
                    BINANCE_REST_URL, BinanceMarketApiResponse.class
            );

            List<String> markets = Arrays.stream(response.getBody().getSymbols())
                    .filter(market -> market.getSymbol().endsWith("USDT"))  // USDT 마켓만 필터링
                    .map(market -> market.getSymbol().toLowerCase() + "@ticker")  // WebSocket 구독용 포맷
                    .collect(Collectors.toList());

            log.info("조회된 USDT 마켓 수: {}", markets.size());
            return markets;
        } catch (Exception e) {
            log.error("마켓 목록 조회 실패", e);
            return List.of();
        }
    }
}
