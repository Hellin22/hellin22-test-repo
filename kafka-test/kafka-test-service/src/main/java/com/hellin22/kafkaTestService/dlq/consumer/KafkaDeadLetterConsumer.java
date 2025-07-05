package com.hellin22.kafkaTestService.dlq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class KafkaDeadLetterConsumer {
    @KafkaListener(topics = "dead-letter-topic", groupId = "dlq-group", containerFactory = "dlqListenerContainerFactory")
    public void dlqListener(String message) {
        log.info("DLQ MESSAGE: {}", message);
    }
}
