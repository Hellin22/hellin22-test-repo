package com.hellin22.rabbitmq_amqp_test_notification_service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class RabbitmqAmqpTestNotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqAmqpTestNotificationServiceApplication.class, args);
    }

}
