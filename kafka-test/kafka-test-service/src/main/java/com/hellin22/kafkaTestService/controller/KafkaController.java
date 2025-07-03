package com.hellin22.kafkaTestService.controller;

import com.hellin22.kafkaTestService.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaProducer kafkaProducer;

    @PostMapping("/send")
    public String send(@RequestParam String message) {
        kafkaProducer.sendMessage("test-topic", message);
        return "전송됨: " + message;
    }
}
