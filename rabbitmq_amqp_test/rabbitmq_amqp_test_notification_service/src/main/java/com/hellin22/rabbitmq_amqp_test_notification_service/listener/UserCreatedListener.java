package com.hellin22.rabbitmq_amqp_test_notification_service.listener;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserCreatedListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("chat-service.user.created.queue"),
            exchange = @Exchange(name = "user.events", type = "direct"),
            key = "user.created"
    ))
    public void onUserCreated(String msg) {
        System.out.println("msg: " + msg);
    }
    // 경국 Map을 전달해서 문제였음. 이거는 convertor를 잘 달아서 해결 가능할듯?
//    public void onUserCreated(Map<String, Object> message) {
//        System.out.println(message.get("userId") + "라는 아이디를 가진 유저가 생성되었습니다. " + message);
//    }
}
