package com.myprohect.springsecurityoauth2jwt.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OAuthToken {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Integer expiresIn;
    private Integer refreshTokenExpiresIn;
}
