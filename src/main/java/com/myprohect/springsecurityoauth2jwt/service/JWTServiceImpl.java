package com.myprohect.springsecurityoauth2jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.myprohect.springsecurityoauth2jwt.constant.JwtConstant;
import com.myprohect.springsecurityoauth2jwt.model.JWToken;
import com.myprohect.springsecurityoauth2jwt.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {
    private int accessTokenExpMinutes = 30;
    private int refreshTokenExpMinutes = 1000;

    private final JWTVerifier verifier;

    @Override
    public JWToken createToken(User user) {
        String accessToken = generateToken(user.getId(), user.getEmail(), accessTokenExpMinutes, JwtConstant.TOKEN_TYPE_ACCESS);
        String refreshToken = generateToken(user.getId(), user.getEmail(), refreshTokenExpMinutes, JwtConstant.TOKEN_TYPE_REFRESH);

        return JWToken.builder().accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(accessTokenExpMinutes*60) // 초 단위여서 * 60
                .refreshTokenExpiresIn(refreshTokenExpMinutes*60) // 초 단위여서 * 60
                .build();
    }

    @Override
    public User validateToken(String accessToken) {

        return null;
    }

    @Override
    public String refreshToken(String refreshToken) {

        return null;
    }

    // 토큰 생성
    public String generateToken(Integer id, String email, int expMinutes, String tokenType) {
        Date tokenExp = new Date(System.currentTimeMillis() + (60000 * expMinutes));

        String generatedToken = JWT.create()
                .withSubject(String.valueOf(id))
                .withClaim("email", email)
                .withClaim("tokenType", tokenType)
                .withExpiresAt(tokenExp)
                .sign(Algorithm.HMAC512(JwtConstant.SECRET_KEY));

        return generatedToken;
    }
}
