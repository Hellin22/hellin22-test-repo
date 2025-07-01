package com.hellin22.rabbitmq_stress_test.controller;

import com.hellin22.rabbitmq_stress_test.dto.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.{roomId}") // /app/chat.{roomId}
    public void send(@DestinationVariable String roomId, ChatMessage message) {
        log.info(roomId + ": " + message);
        messagingTemplate.convertAndSend("/topic/room." + roomId, message);
    }

    @MessageExceptionHandler
    public void handleException(Throwable ex) {
        log.error("Exception in STOMP handler", ex);
    }
}
