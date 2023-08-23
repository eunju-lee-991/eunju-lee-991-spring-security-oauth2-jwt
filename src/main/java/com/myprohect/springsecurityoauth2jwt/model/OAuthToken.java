package com.myprohect.springsecurityoauth2jwt.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthToken {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private int expiresIn;
    private int refreshTokenExpiresIn;
}
