package com.arbiter.pricecollector.service.message;

import com.arbiter.core.model.PriceMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, PriceMessageDto> kafkaTemplate;
    private static final String TOPIC = "price_topic";

    public void sendPrice(PriceMessageDto priceMessage) {
        kafkaTemplate.send(TOPIC, priceMessage);
        log.info("[Kafka] 메시지 전송: {}", priceMessage);
    }
}
