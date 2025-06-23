package com.hellin22.rabbitmq_amqp_test_notification_service.listener;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserCreatedListener {

    // 1. exchange type = direct
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("chat-service.user.created.queue"),
            exchange = @Exchange(name = "user.events", type = "direct"),
            key = "user.created"
    ))
    public void onUserCreated(String msg) {
        System.out.println("msg: " + msg);
    }


    // ------------topic---------------

    // 2-1. exchange type = topic
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("chat-topic.not-pattern"),
            exchange = @Exchange(name = "topic.event", type = "topic"),
            key = "topic.log1"
    ))
    public void topicListener(String log){
        System.out.println("not 패턴매칭 topic listener: " + log);
    }

    // 2-2. exchange type = topic
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("chat-topic.all-pattern"),
            exchange = @Exchange(name = "topic.event", type = "topic"),
            key = "topic.*"
    ))
    public void allTopicListener(String log){
        System.out.println("패턴매칭 topic listener: " + log);
    }


    // ------------fanout---------------

    // 3-1. exchange type = fanout
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("chat-fanout.1"),
            exchange = @Exchange(name = "fanout.event", type = "fanout"),
            key = "fanout.log1" // fanout은 routing key에 상관없이 broadcast
    ))
    public void allFanoutListener1(String broadCastLog){
        System.out.println("fanout1의 broadCastLog: " + broadCastLog);
    }

    // 3-2. exchange type = fanout
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("chat-fanout.2"),
            exchange = @Exchange(name = "fanout.event", type = "fanout"),
            key = "fanout.log2" // fanout은 routing key에 상관없이 broadcast
    ))
    public void allFanoutListener2(String broadCastLog){
        System.out.println("fanout2의 broadCastLog: " + broadCastLog);
    }




    // 4. exchange type = headers
    // headers의 경우 명시적 제어(Bean 등록이 필수)
    public void headers(String headers){
        System.out.println("headers의 headers: " + headers);
    }
}
