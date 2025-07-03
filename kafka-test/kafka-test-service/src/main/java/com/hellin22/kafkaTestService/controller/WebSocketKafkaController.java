package com.hellin22.kafkaTestService.controller;

import com.hellin22.kafkaTestService.dto.ChatMessage;
import com.hellin22.kafkaTestService.producer.KafkaMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketKafkaController {

    private final KafkaMessageProducer kafkaProducer;

    @MessageMapping("/send")
    public void handleMessage(ChatMessage message) {
        kafkaProducer.send("chat-topic", message);
    }
}
