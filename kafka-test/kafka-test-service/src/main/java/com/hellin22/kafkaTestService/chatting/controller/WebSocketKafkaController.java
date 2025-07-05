package com.hellin22.kafkaTestService.chatting.controller;

import com.hellin22.kafkaTestService.chatting.dto.ChatMessage;
import com.hellin22.kafkaTestService.chatting.producer.KafkaMessageProducer;
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
