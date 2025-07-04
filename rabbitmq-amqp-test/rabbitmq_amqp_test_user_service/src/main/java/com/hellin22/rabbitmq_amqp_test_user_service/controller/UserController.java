package com.hellin22.rabbitmq_amqp_test_user_service.controller;

import com.hellin22.rabbitmq_amqp_test_user_service.dto.MemberDto;
import com.hellin22.rabbitmq_amqp_test_user_service.publisher.UserEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserEventPublisher eventPublisher;

    @Autowired
    public UserController(UserEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/users")
    public String createUser(@RequestParam String memberId,
                             @RequestParam String memberName) {
        try {
            eventPublisher.publishUserCreated(new MemberDto(memberId, memberName)); // 비동기 이벤트 발행
            return memberId + " " + memberName + "를 가진 유저가 생성될거에요 !!";
        } catch (Exception e) {
            throw new RuntimeException("유저 생성 실패했습니다.", e);
        }
    }

    // exchange type = topic
    @PostMapping("/topic")
    public String createTopic(@RequestParam String memberId,
                              @RequestParam String memberName) {
        try {
            eventPublisher.publishTopicLog(new MemberDto(memberId, memberName));
            return memberId + " " + memberName + "의 topic 이벤트";
        } catch (Exception e) {
            throw new RuntimeException("유저 생성 실패했습니다.", e);
        }
    }

    // exchange type = fanout
    @PostMapping("/fanout")
    public String createFanout(@RequestParam String memberId,
                               @RequestParam String memberName) {
        try {
            eventPublisher.publishFanoutLog(new MemberDto(memberId, memberName));
            return memberId + " " + memberName + "의 fanout 이벤트";
        } catch (Exception e) {
            throw new RuntimeException("유저 생성 실패했습니다.", e);
        }
    }

    // exchange type = headers
    @PostMapping("/headers")
    public String createHeaders(@RequestParam String memberId,
                                @RequestParam String memberName) {
        try {
            eventPublisher.publishHeadersLog(new MemberDto(memberId, memberName));
            return memberId + " " + memberName + "의 header 이벤트";
        } catch (Exception e) {
            throw new RuntimeException("유저 생성 실패했습니다.", e);
        }
    }
}
