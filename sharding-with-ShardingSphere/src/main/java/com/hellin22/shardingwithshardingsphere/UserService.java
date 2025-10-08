package com.hellin22.shardingwithshardingsphere;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(String userName) {

        User user = new User(userName);

        return userRepository.save(user);
    }

    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User getUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Transactional
    public User getuserById(Long userid) {
        return userRepository.findById(userid).orElse(null);
    }

}
