package com.hellin22.shardingwithshardingsphere;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long ueserId;

    @Column(name = "user_name")
    private String userName;

    public User(String userName) {
        this.userName = userName;
    }
}
