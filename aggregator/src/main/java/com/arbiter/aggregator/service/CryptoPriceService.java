package com.arbiter.aggregator.service;

import com.arbiter.aggregator.model.BinanceCryptoPrice;
import com.arbiter.aggregator.model.UpbitCryptoPrice;
import com.arbiter.aggregator.model.repository.BinanceCryptoPriceRepository;
import com.arbiter.aggregator.model.repository.UpbitCryptoPriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CryptoPriceService {
    private final BinanceCryptoPriceRepository binanceCryptoPriceRepository;
    private final UpbitCryptoPriceRepository upbitCryptoPriceRepository;

    public Optional<UpbitCryptoPrice> getUpbitPrice(String ticker) {
        return upbitCryptoPriceRepository.findById(ticker);
    }

    public Optional<BinanceCryptoPrice> getBinancePrice(String ticker) {
        return binanceCryptoPriceRepository.findById(ticker);
    }
}
