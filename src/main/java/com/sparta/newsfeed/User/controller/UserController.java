package com.sparta.newsfeed.User.controller;


import com.sparta.newsfeed.Common.security.UserDetailsImpl;
import com.sparta.newsfeed.User.dto.*;
import com.sparta.newsfeed.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/newsfeed")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/sign-up")
    public String createUser(SignupRequestDto requestDto){
        userService.createUser(requestDto);
        return "redirect:/newsfeed/user/login-page";
    }

    @GetMapping("/user-info")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        String nickname = userDetails.getUser().getNickname();
        String myContent = userDetails.getUser().getMy_content();
        Long user_id = userDetails.getUser().getId();
        return new UserInfoDto(username,nickname,myContent,user_id);
    }

    @PutMapping("/introduce")
    @ResponseBody
    public IntroduceResponseDto editIntroduce(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody IntroduceRequestDto requestDto){
        return userService.editIntroduce(userDetails.getUser().getId(), requestDto);
    }

    @PostMapping("/password")
    @ResponseBody
    public CheckPasswordResponseDto checkPassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CheckPasswordRequestDto requestDto){
        CheckPasswordResponseDto responseDto = new CheckPasswordResponseDto(userService.checkPassword(userDetails, requestDto));
        return responseDto;
    }

    @PutMapping("/password")
    @ResponseBody
    public SignupResponseDto editPassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody EditPasswordRequestDto requestDto){
        return  userService.editPassword(userDetails.getUser(),requestDto);
    }


}