package com.myprohect.springsecurityoauth2jwt.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {
    @GetMapping("/")
    public String index(){
        System.out.println("index");
        return "Index";
    }
    @GetMapping("/test")
    public String test(){
        System.out.println("test");
        return "String";
    }

    @GetMapping("/api/login/code")
    public String getCode(@RequestParam String code, @RequestParam String provider) {
        System.out.println(code + " & " + provider);
        return code + " & " + provider;
    }

    @GetMapping("/user")
    public String user(){
        System.out.println("useruser");
        return "useruser";
    }

    @GetMapping("/api/login")
    public String apiLogin(){
        System.out.println("apiLogin");
        return "apiLogin";
    }
}
