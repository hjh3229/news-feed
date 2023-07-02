package com.sparta.newsfeed.User.controller;


import com.sparta.newsfeed.User.dto.SignupRequestDto;
import com.sparta.newsfeed.User.dto.SignupResponseDto;
import com.sparta.newsfeed.User.service.UserService;
import lombok.RequiredArgsConstructor;
amework.web.bind.annotation.RestController;
=======
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/newsfeed/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public SignupResponseDto createUser(@RequestBody SignupRequestDto requestDto){
        return userService.createUser(requestDto);
    }


}
