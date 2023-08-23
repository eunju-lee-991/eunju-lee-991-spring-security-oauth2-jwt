package com.myprohect.springsecurityoauth2jwt.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String profileImage;
    private String role;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String provider;
    private String providerId;
}
