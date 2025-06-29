package com.hellin22.docker_network_test_app1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class App1Controller {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/call-app2")
    public String callApp2(@RequestParam String url) {
        try {
            String response = restTemplate.getForObject("http://" + url + ":8080/ping", String.class);
            return "app2에서 넘어온 데이터는?? : " + response;
        } catch (Exception e) {
            return "연결 실패(같은 네트워크 상에 있지 않습니다): " + e.getMessage();
        }
    }
}

