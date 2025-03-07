package com.arbiter.aggregator.service;

import com.arbiter.aggregator.model.BinanceCryptoPrice;
import com.arbiter.aggregator.model.UpbitCryptoPrice;
import com.arbiter.aggregator.model.repository.BinanceCryptoPriceRepository;
import com.arbiter.aggregator.model.repository.UpbitCryptoPriceRepository;
import com.arbiter.core.constant.ErrorCode;
import com.arbiter.core.exception.CoinPendingCreationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CryptoPriceService {
    private final BinanceCryptoPriceRepository binanceCryptoPriceRepository;
    private final UpbitCryptoPriceRepository upbitCryptoPriceRepository;

    public UpbitCryptoPrice getTetherKRW() {
        return upbitCryptoPriceRepository.findById("USDT")
                .orElseThrow(() -> new CoinPendingCreationException(ErrorCode.COIN_PENDING_CREATION));
    }

    public UpbitCryptoPrice getUpbitPrice(String ticker) {
        return upbitCryptoPriceRepository.findById(ticker)
                .orElseThrow(() -> new CoinPendingCreationException(ErrorCode.COIN_PENDING_CREATION));
    }

    public BinanceCryptoPrice getBinancePrice(String ticker) {
        return binanceCryptoPriceRepository.findById(ticker)
                .orElseThrow(() -> new CoinPendingCreationException(ErrorCode.COIN_PENDING_CREATION));
    }
}
