package com.arbiter.aggregator.service;

import com.arbiter.aggregator.model.BinanceCryptoPrice;
import com.arbiter.aggregator.model.UpbitCryptoPrice;
import com.arbiter.core.model.KimpMessageDto;
import com.arbiter.core.model.PriceMessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.arbiter.core.util.MessageToPriceMessagingDtoConverter.convertToPriceMessage;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final ObjectMapper objectMapper;
    private final CryptoPriceService cryptoPriceService;
    private final KimpCalculateService kimpCalculateService;
    private final KafkaProducerService kafkaProducerService;
    private final PriceSyncService priceSyncService;

    @KafkaListener(topics = "price_topic", groupId = "tst", containerFactory = "kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, String> record) {
        // 메시지 수신
        String message = record.value();
        log.info("[KafkaConsumerService] Received {}", message);

        // JSON -> DTO 변환
        PriceMessageDto priceMessage = convertToPriceMessage(message, objectMapper);

        // 레디스 캐시 가격 갱신 (:TODO 비동기 처리)
        priceSyncService.sync(priceMessage);

        // 테더, 해외 코인 조회
        UpbitCryptoPrice tether = cryptoPriceService.getTetherKRW();

        double kimp;
        if (priceMessage.getMarket().equals("UPBIT")) {
            BinanceCryptoPrice binancePrice = cryptoPriceService.getBinancePrice(priceMessage.getTicker());

            Double koreanPrice = priceMessage.getPrice();
            Double globalPrice = binancePrice.getPrice();
            kimp = kimpCalculateService.calculateKimpPrice(koreanPrice, globalPrice, tether.getPrice());
        } else {
            UpbitCryptoPrice upbitPrice = cryptoPriceService.getUpbitPrice(priceMessage.getTicker());
            Double koreanPrice = upbitPrice.getPrice();
            Double globalPrice = priceMessage.getPrice();
            kimp = kimpCalculateService.calculateKimpPrice(koreanPrice, globalPrice, tether.getPrice());
        }

        KimpMessageDto kimpMessageDto = new KimpMessageDto();
        kimpMessageDto.of(priceMessage, kimp);
        kafkaProducerService.sendPrice(kimpMessageDto);
    }


}
