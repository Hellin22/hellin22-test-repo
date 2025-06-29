package com.hellin22.rabbitmq_stress_test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")  // 클라이언트가 연결할 URI
                .setAllowedOriginPatterns("*") // 개발 중 허용
                .addInterceptors(new WebSocketHandshakeInterceptor());
//                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableStompBrokerRelay("/topic", "/queue")
                .setRelayHost("rabbitmq") // RabbitMQ 주소
                .setRelayPort(61613) // STOMP 포트
                .setClientLogin("guest")
                .setClientPasscode("guest");

        // RabbitMQ는 /가 아닌 .을 지원하기 떄문에 설정
        registry.setPathMatcher(new AntPathMatcher("."));

        // 메시지를 발행(송신 - publish)할때 사용하는 prefix 설정
        registry.setApplicationDestinationPrefixes("/app");
//        registry.enableSimpleBroker("/topic", "/queue"); // relay 말고 simple
//        registry.setApplicationDestinationPrefixes("/app");
    }
}
