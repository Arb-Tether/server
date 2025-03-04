package com.arbiter.pricecollector.service.client;

import com.arbiter.pricecollector.model.api.response.MarketApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class UpbitMarketSearchClient {
    private static final String UPBIT_REST_URL = "https://api.upbit.com/v1/market/all";

    private final RestTemplate restTemplate;

    public List<String> getAllKRWMarkets() {
        try {
            log.info("업비트 마켓 목록 조회 시도...");
            ResponseEntity<MarketApiResponse[]> response = restTemplate.getForEntity(UPBIT_REST_URL, MarketApiResponse[].class);
            List<String> markets = Arrays.stream(response.getBody())
                    .filter(market -> market.market.startsWith("KRW-"))
                    .map(market -> market.market)
                    .collect(Collectors.toList());
            log.info("조회된 KRW 마켓 수: {}", markets.size());
            return markets;
        } catch (Exception e) {
            log.error("마켓 목록 조회 실패", e);
            return new ArrayList<>();
        }
    }
}
