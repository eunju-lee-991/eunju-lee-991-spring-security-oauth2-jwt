package com.myprohect.springsecurityoauth2jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

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
