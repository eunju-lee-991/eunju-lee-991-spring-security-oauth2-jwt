package com.myprohect.springsecurityoauth2jwt.service;

import com.myprohect.springsecurityoauth2jwt.model.OAuthToken;
import com.myprohect.springsecurityoauth2jwt.model.User;
import org.springframework.stereotype.Service;

import java.io.IOException;


public interface OAuth2ProcessService {

    // 액세스 토큰 받기
    OAuthToken getToken(String code, String provider) throws IOException;

    // 유저 프로필 받기
    User getUserProfile(String accessToken, String provider) throws IOException;

    // 그렇기 때문에 갱신 토큰은 접근 토큰이 만료될 것을 대비하여 데이터베이스에 별도로 저장하고 이후 필요에 따라 갱신 토큰을 사용하면 됩니다.
    // 토큰 갱신
    OAuthToken refreshAccessToken(String refreshToken, String provider) throws IOException;
}
