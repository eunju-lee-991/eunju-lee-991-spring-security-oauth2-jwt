package com.myprohect.springsecurityoauth2jwt.service;

import com.myprohect.springsecurityoauth2jwt.model.JWToken;
import com.myprohect.springsecurityoauth2jwt.model.User;
import org.springframework.stereotype.Service;

@Service
public interface JWTservice {

    // 토큰 발급
    JWToken createToken(User user);

    // 토큰 검증
    User validateToken(String accessToken);

    // 토큰 리프레쉬
    String refreshToken(String refreshToken);
}
