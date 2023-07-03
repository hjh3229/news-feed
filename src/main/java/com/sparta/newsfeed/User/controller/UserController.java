package com.sparta.newsfeed.User.controller;


import com.sparta.newsfeed.User.dto.IntroduceRequestDto;
import com.sparta.newsfeed.User.dto.IntroduceResponseDto;
import com.sparta.newsfeed.User.dto.SignupRequestDto;
import com.sparta.newsfeed.User.dto.SignupResponseDto;
import com.sparta.newsfeed.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/newsfeed/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public void createUser(@RequestBody SignupRequestDto requestDto){
        userService.createUser(requestDto);
    }

    @PutMapping("/introduce/{user_id}")
    public IntroduceResponseDto editIntroduce(@PathVariable Long id,@RequestBody IntroduceRequestDto requestDto){
        return userService.editIntroduce(id,requestDto);
    }

    @GetMapping("/introduce/{user_id}")
    public IntroduceResponseDto selecteIntroduce(@PathVariable Long id){
        return userService.selecteIntroduce(id);
    }

//    @PutMapping("/edit-password")
//    public SignupResponseDto editPassword(@RequestBody SignupRequestDto requestDto){
//        return  userService.editPassword(requestDto);
//    }

}
