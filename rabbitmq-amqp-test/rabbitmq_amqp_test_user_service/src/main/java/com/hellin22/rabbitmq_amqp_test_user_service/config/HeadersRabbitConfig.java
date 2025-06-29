package com.hellin22.rabbitmq_amqp_test_user_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class HeadersRabbitConfig {

    // exchange name 설정
    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange("headers.event");
    }

    // queue 이름 명시적 설정
    @Bean
    public Queue criticalQueue() {
        return new Queue("chat-header.onlyheader");
    }

    @Bean
    public Binding bindingCritical(Queue criticalQueue, HeadersExchange headersExchange) {
        return BindingBuilder.bind(criticalQueue)
                .to(headersExchange)
                .whereAll(Map.of(
                        "k1", "v1",
                        "k2", "v2"
                ))
                .match();
    }


}
