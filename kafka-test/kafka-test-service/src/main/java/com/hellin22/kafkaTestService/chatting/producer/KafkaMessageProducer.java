package com.hellin22.kafkaTestService.chatting.producer;

import com.hellin22.kafkaTestService.chatting.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaMessageProducer {

    private final KafkaTemplate<String, ChatMessage> kafkaTemplate;

    public void send(String topic, ChatMessage message) {
        kafkaTemplate.send(topic, message);
    }
}