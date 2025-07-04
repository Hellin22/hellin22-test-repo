package com.hellin22.kafkaTestService.chatting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String roomId;    // 채팅방 ID
    private String sender;    // 보낸 사람
    private String content;   // 메시지 내용
}
