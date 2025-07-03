package com.hellin22.kafkaTestService.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void consume(String message) {
        System.out.println("Kafka 수신 메시지: " + message);
    }
}