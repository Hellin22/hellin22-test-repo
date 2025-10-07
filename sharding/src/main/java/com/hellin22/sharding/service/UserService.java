package com.hellin22.sharding.service;

import com.hellin22.sharding.config.RoutingDataSource;
import com.hellin22.sharding.entity.User;
import com.hellin22.sharding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * userId를 기반으로 샤드 결정 (id % 3)
     */
    private String determineShardKey(Long userId) {
        int shardNumber = (int) (userId % 3);
        return "shard" + shardNumber;
    }

    @Transactional(readOnly = true)
    public User findById(Long userId) {
        try {
            String shardKey = determineShardKey(userId);
            RoutingDataSource.setDataSourceKey(shardKey);

            return userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        } finally {
            RoutingDataSource.clearDataSourceKey();
        }
    }

    @Transactional
    public User save(User user) {
        try {
            String shardKey = determineShardKey(user.getId());
            RoutingDataSource.setDataSourceKey(shardKey);

            return userRepository.save(user);
        } finally {
            RoutingDataSource.clearDataSourceKey();
        }
    }
}