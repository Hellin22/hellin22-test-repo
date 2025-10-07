package com.hellin22.shardingwithshardingsphere;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public User createUser(@RequestParam String username) {
        return userService.createOrder(username);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
}
