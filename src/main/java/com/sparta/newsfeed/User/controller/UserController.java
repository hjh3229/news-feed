package com.sparta.newsfeed.User.controller;


import com.sparta.newsfeed.User.dto.SignupRequestDto;
import com.sparta.newsfeed.User.dto.SignupResponseDto;
import com.sparta.newsfeed.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/newsfeed/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public SignupResponseDto createUser(@ModelAttribute SignupRequestDto requestDto){
        return userService.createUser(requestDto);
    }
}
