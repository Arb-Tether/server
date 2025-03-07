package com.arbiter.aggregator.service;

import com.arbiter.aggregator.model.BinanceCryptoPrice;
import com.arbiter.aggregator.model.UpbitCryptoPrice;
import com.arbiter.aggregator.model.repository.BinanceCryptoPriceRepository;
import com.arbiter.aggregator.model.repository.UpbitCryptoPriceRepository;
import com.arbiter.core.model.PriceMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PriceSyncService {
    private final UpbitCryptoPriceRepository upbitCryptoPriceRepository;
    private final BinanceCryptoPriceRepository binanceCryptoPriceRepository;

    public void sync(PriceMessageDto messageDto) {
        String market = messageDto.getMarket();
        if (market.equals("UPBIT")) {
            UpbitCryptoPrice upbitCryptoPrice = new UpbitCryptoPrice(messageDto.getTicker(), messageDto.getPrice(), messageDto.getTimestamp());
            upbitCryptoPriceRepository.save(upbitCryptoPrice);
        } else {
            BinanceCryptoPrice binanceCryptoPrice = new BinanceCryptoPrice(messageDto.getTicker(), messageDto.getPrice(), messageDto.getTimestamp());
            binanceCryptoPriceRepository.save(binanceCryptoPrice);
        }
    }
}
