package com.arbiter.aggregator.service;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String EXCHANGE_API_URL = "https://api.exchangerate-api.com/v4/latest/USD";

    // ✅ 최신 환율을 저장하는 캐시
    private volatile double cachedUsdToKrw = 1350.0; // 기본값 (초기화)

    @PostConstruct
    public void initExchangeRate() {
        log.info("🚀 애플리케이션 시작: 최초 환율 업데이트 실행...");
        updateExchangeRate(); // 최초 실행
    }

    // ✅ 10분마다 환율 업데이트
    @Scheduled(fixedRate = 600000) // 10분마다 실행 (600,000ms = 10분)
    public void updateExchangeRate() {
        try {
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(EXCHANGE_API_URL, JsonNode.class);
            double newRate = response.getBody().get("rates").get("KRW").asDouble();
            log.info("✅ USD-KRW 환율 갱신: {}", newRate);
            cachedUsdToKrw = newRate;
        } catch (Exception e) {
            log.error("❌ 환율 정보 조회 실패, 기존 값 유지: {}", cachedUsdToKrw, e);
        }
    }

    // ✅ 최신 환율 가져오기
    public double getCachedForexUSDTtoKRW() {
        return cachedUsdToKrw;
    }
}


