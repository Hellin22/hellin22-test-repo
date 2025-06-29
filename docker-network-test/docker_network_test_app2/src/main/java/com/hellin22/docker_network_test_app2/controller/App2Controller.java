package com.hellin22.docker_network_test_app2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class App2Controller {

    @GetMapping("/ping")
    public String ping() {
        return "pong from app2";
    }
}
