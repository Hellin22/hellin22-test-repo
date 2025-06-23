package com.hellin22.rabbitmq_amqp_test_user_service.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellin22.rabbitmq_amqp_test_user_service.dto.MemberDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public UserEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishUserCreated(MemberDto memberDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String objectToJSON = objectMapper.writeValueAsString(memberDto);

        rabbitTemplate.convertAndSend("user.events", "user.created", memberDto);
    }
}

