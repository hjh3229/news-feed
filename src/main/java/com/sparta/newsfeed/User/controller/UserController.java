package com.sparta.newsfeed.User.controller;


import com.sparta.newsfeed.Common.security.UserDetailsImpl;
import com.sparta.newsfeed.User.dto.IntroduceRequestDto;
import com.sparta.newsfeed.User.dto.IntroduceResponseDto;
import com.sparta.newsfeed.User.dto.SignupRequestDto;
import com.sparta.newsfeed.User.dto.SignupResponseDto;
import com.sparta.newsfeed.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/newsfeed")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/sign-up")
    public SignupResponseDto createUser(@RequestBody SignupRequestDto requestDto){
        return userService.createUser(requestDto);
    }

    @PutMapping("/introduce")
    public IntroduceResponseDto editIntroduce(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody IntroduceRequestDto requestDto){
        return userService.editIntroduce(userDetails.getUser().getId(), requestDto);
    }

    @GetMapping("/user/introduce")
    public IntroduceResponseDto selecteIntroduce(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.selecteIntroduce(userDetails.getUser().getId());
    }

    @PutMapping("/password")
    public SignupResponseDto editPassword(@AuthenticationPrincipal UserDetailsImpl userDetails,@RequestBody SignupRequestDto requestDto){
        return  userService.editPassword(userDetails.getUser(),requestDto);
    }

}