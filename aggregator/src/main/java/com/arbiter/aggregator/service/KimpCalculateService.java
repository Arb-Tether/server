package com.arbiter.aggregator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KimpCalculateService {
    public double calculateKimpPrice(double upbit, double binance, double usdtkrw) {
        log.info("calculate kimp price");
        log.info("upbit : {}, binance : {}, usdtkrw : {}", upbit, binance, usdtkrw);
        return (upbit - (binance * usdtkrw)) / (binance * usdtkrw) * 100;
    }
}
