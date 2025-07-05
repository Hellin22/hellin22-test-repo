package com.hellin22.kafkaTestService.dlq.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaDlqProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public String send(String message){
        kafkaTemplate.send("dlq-test-topic", message);

        return "sent: " + message;
    }
}
