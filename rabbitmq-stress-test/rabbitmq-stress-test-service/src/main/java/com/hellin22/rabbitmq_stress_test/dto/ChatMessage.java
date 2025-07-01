package com.hellin22.rabbitmq_stress_test.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatMessage {

    private String sender;
    private String message;
}
