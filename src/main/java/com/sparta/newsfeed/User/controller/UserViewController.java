package com.sparta.newsfeed.User.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    @GetMapping("/newsfeed/user/login-page")
    public String login(){
        return "login";
    }

    @GetMapping("/newsfeed/user/signup-page")
    public String signup(){
        return "signup";
    }

}
