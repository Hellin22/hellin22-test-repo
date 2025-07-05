package com.hellin22.kafkaTestService.dlq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaDlqConsumer {

    @KafkaListener(
            topics = "dlq-test-topic", groupId = "dlq-test-group",
            containerFactory = "dlqListenerContainerFactory"
    )
    public void listen(String message, Acknowledgment ack) {
        // Manual ack
        log.info("received message: {}", message);

        if(message.contains("fail")){
            throw new RuntimeException("의도적인 실패");
        }

        ack.acknowledge();
    }
}
