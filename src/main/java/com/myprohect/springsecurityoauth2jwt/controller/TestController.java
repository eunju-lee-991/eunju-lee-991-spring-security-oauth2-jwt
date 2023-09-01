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

    @GetMapping("/api/test/anyone")
    public String anyone(){
        System.out.println("anyone");

        return "everybody is welcomed";
    }

    @GetMapping("/api/test/user")
    public String user(){
        System.out.println("user user");
        return "user user";
    }
    @GetMapping("/api/test/manager")
    public String manager(){
        System.out.println("manager manager");
        return "manager manager";
    }
    @GetMapping("/api/test/admin")
    public String admin(){
        System.out.println("admin admin");
        return "admin admin";
    }
}
