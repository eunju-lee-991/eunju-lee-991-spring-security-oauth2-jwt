package com.myprohect.springsecurityoauth2jwt.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWToken {
    private String accessToken;
    private String refreshToken;
}
