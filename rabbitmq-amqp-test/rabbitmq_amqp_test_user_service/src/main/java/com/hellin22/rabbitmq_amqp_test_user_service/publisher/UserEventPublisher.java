package com.hellin22.rabbitmq_amqp_test_user_service.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellin22.rabbitmq_amqp_test_user_service.dto.MemberDto;
import org.springframework.amqp.core.MessageProperties;
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

    // 1. direct -> exchange name, routing key가 정확히 일치
    public void publishUserCreated(MemberDto memberDto) throws JsonProcessingException {
        rabbitTemplate.convertAndSend("user.events", "user.created", memberDto);
    }

    // 2. topic -> routing key의 패턴매칭 가능
    public void publishTopicLog(MemberDto memberDto) throws JsonProcessingException {
        rabbitTemplate.convertAndSend("topic.event", "topic.log1", memberDto);
        rabbitTemplate.convertAndSend("topic.event", "topic.log2", memberDto);
    }

    // 3. fanout -> exchange name만 일치하면 broadcast
    public void publishFanoutLog(MemberDto memberDto) throws JsonProcessingException {
        rabbitTemplate.convertAndSend("fanout.event", "", memberDto);
    }

    // 4. headers -> header는 명시적으로 큐 생성 필요
    public void publishHeadersLog(MemberDto memberDto) throws JsonProcessingException {
        MessageProperties props = new MessageProperties();
        props.setHeader("k1", "v1");  // 헤더 1
        props.setHeader("k2", "v2");     // 헤더 2
        rabbitTemplate.convertAndSend("headers.event", "", memberDto);
    }
}

