package com.hellin22.sharding.controller;

import com.hellin22.sharding.entity.User;
import com.hellin22.sharding.entity.dto.UserCreateRequest;
import com.hellin22.sharding.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserCreateRequest request) {
        User user = new User();
        user.setId(request.getId()); // 샤딩을 위해 ID 필요
        user.setName(request.getUsername());
        user.setEmail(request.getEmail());

        User savedUser = userService.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = userService.findById(userId);
        return ResponseEntity.ok(user);
    }
}
