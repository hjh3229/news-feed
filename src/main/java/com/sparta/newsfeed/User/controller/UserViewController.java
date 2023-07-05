package com.sparta.newsfeed.User.controller;

import com.sparta.newsfeed.Common.security.UserDetailsImpl;
import com.sparta.newsfeed.User.dto.IntroduceResponseDto;
import com.sparta.newsfeed.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class UserViewController {

    private final UserService userService;

    @GetMapping("/newsfeed/user/login-page")
    public String login(){
        return "login";
    }

    @GetMapping("/newsfeed/user/signup-page")
    public String signup(){
        return "signup";
    }

    @GetMapping("/newsfeed/user/introduce")
    public String selecteIntroduce(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        IntroduceResponseDto userintro = userService.selecteIntroduce(userDetails.getUser().getId());
        model.addAttribute("userintro",userintro);
        return "userintro";
    }

}
