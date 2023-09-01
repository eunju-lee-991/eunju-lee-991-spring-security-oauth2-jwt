package com.myprohect.springsecurityoauth2jwt.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JWToken {
    private String accessToken;
    private String refreshToken;
    private Integer accessTokenExpiresIn;
    private Integer refreshTokenExpiresIn;
}
