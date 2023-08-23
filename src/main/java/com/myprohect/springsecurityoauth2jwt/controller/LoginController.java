package com.myprohect.springsecurityoauth2jwt.controller;

import com.myprohect.springsecurityoauth2jwt.model.JWToken;
import com.myprohect.springsecurityoauth2jwt.model.OAuthToken;
import com.myprohect.springsecurityoauth2jwt.model.User;
import com.myprohect.springsecurityoauth2jwt.service.JWTservice;
import com.myprohect.springsecurityoauth2jwt.service.OAuth2ProcessService;
import com.myprohect.springsecurityoauth2jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final OAuth2ProcessService oAuth2ProcessService;
    private final UserService userService;
    private final JWTservice jwTservice;

    @GetMapping("/api/login/code")
    public String getCode(@RequestParam String code, @RequestParam String provider) {
        System.out.println(code + " & " + provider);

        // 토큰 받아옴
        OAuthToken oAuthToken = oAuth2ProcessService.getToken(code, provider);

        // 유저 프로필 받아옴
        User userProfile = oAuth2ProcessService.getUserProfile(oAuthToken.getAccessToken(), provider);

        User user = new User();

        // 회원가입 or 로그인
        int id = userService.join(user);

        // JWT 토큰 발급
        JWToken jwToken = jwTservice.createToken(user);



        return "로그인 성공";
    }


    @GetMapping("/api/logout")
    public String logout() {

        return "logout";
    }
}
