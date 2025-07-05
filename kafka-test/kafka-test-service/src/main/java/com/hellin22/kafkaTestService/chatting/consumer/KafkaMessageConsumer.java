package com.hellin22.kafkaTestService.chatting.consumer;

import com.hellin22.kafkaTestService.chatting.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageConsumer {

    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "chat-topic", groupId = "websocket-group")
    public void consume(ChatMessage message) {
        log.info("Used message channel = {}", messagingTemplate.getMessageChannel().getClass());
        // 메시지를 채팅방 ID 기준으로 전달 가능
        messagingTemplate.convertAndSend("/topic/chat/" + message.getRoomId(), message);
    }
}
