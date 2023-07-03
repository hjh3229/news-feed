package com.sparta.newsfeed.User.controller;


import com.sparta.newsfeed.Common.security.UserDetailsImpl;
import com.sparta.newsfeed.User.dto.SignupRequestDto;
import com.sparta.newsfeed.User.dto.UserInfoDto;
import com.sparta.newsfeed.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/newsfeed")
public class UserController {

    private final UserService userService;

    @PostMapping("user/sign-up")
    public String createUser(SignupRequestDto requestDto){
        userService.createUser(requestDto);
        return "redirect:/newsfeed/user/login-page";
    }

    @GetMapping("/user-info")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();


        return new UserInfoDto(username);
    }

}
