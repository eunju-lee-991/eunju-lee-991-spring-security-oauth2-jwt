package com.myprohect.springsecurityoauth2jwt.controller;

import com.myprohect.springsecurityoauth2jwt.model.JWToken;
import com.myprohect.springsecurityoauth2jwt.model.OAuthToken;
import com.myprohect.springsecurityoauth2jwt.model.User;
import com.myprohect.springsecurityoauth2jwt.service.JWTService;
import com.myprohect.springsecurityoauth2jwt.service.OAuth2ProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final OAuth2ProcessService oAuth2ProcessService;
    private final JWTService jwtService;

    @GetMapping("/api/login/code")
    public ResponseEntity getCode(@RequestParam String code, @RequestParam String provider) throws IOException {
        System.out.println(code + " & " + provider);

        // 토큰 받아옴
        OAuthToken oAuthToken = oAuth2ProcessService.getToken(code, provider);

        // 유저 프로필 받아옴
        User user = oAuth2ProcessService.getUserProfile(oAuthToken.getAccessToken(), provider);

        // JWT 토큰 발급
        JWToken jwToken = jwtService.createToken(user);

        return ResponseEntity.status(HttpStatus.OK).body(jwToken);
    }

    @GetMapping("/api/logout")
    public String logout() {

        return "logout";
    }
}
