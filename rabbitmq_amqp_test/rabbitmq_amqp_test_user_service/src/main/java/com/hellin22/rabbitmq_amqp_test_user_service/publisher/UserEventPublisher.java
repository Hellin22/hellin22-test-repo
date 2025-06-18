package com.hellin22.rabbitmq_amqp_test_user_service.publisher;

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

    public void publishUserCreated(String userId) {

//        Map<String, Object> userData = Map.of(
//                "userId", userId
//        );
        rabbitTemplate.convertAndSend("user.events", "user.created", userId);
    }
}

