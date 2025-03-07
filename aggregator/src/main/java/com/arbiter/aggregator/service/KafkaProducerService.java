package com.arbiter.aggregator.service;

import com.arbiter.core.model.KimpMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, KimpMessageDto> kafkaTemplate;
    private static final String TOPIC = "kimp_topic";

    public void sendPrice(KimpMessageDto kimpMessageDto) {
        kafkaTemplate.send(TOPIC, kimpMessageDto);
        log.info("[Kafka] 메시지 전송: {}", kimpMessageDto);
    }
}
