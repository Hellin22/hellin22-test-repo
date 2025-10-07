package com.hellin22.sharding.entity.dto;


import lombok.Data;

@Data
public class UserCreateRequest {
    private Long id;  // 샤드 결정을 위해 ID 필요
    private String username;
    private String email;
}