package com.hellin22.kafkaTestService.dlq.controller;

import com.hellin22.kafkaTestService.dlq.producer.KafkaDlqProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaDlqController {

    private final KafkaDlqProducer dlqProducer;

    @PostMapping("/dlq")
    public String send(@RequestParam String message) {
        return dlqProducer.send(message);
    }
}
