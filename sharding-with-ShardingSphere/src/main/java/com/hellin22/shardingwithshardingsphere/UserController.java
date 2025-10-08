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
        return userService.createUser(username);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/by-username")
    public User getUser(@RequestParam String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/by-id")
    public User getUserById(@RequestParam Long userid) {
        return userService.getuserById(userid);
    }

}
