package com.sparta.newsfeed.User.controller;


import com.sparta.newsfeed.Common.security.UserDetailsImpl;
import com.sparta.newsfeed.Folder.dto.FolderResponseDto;
import com.sparta.newsfeed.User.service.UserService;
import org.springframework.stereotype.Controller;
import com.sparta.newsfeed.User.dto.*;
import com.sparta.newsfeed.Folder.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/newsfeed")
public class UserController {

    private final UserService userService;
    private final FolderService folderService;

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
        return new UserInfoDto(username,nickname,myContent);
    }

    @PutMapping("/introduce")
    @ResponseBody
    public IntroduceResponseDto editIntroduce(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody IntroduceRequestDto requestDto){
        return userService.editIntroduce(userDetails.getUser().getId(), requestDto);
    }

    @GetMapping("/user/introduce")
    @ResponseBody
    public IntroduceResponseDto selecteIntroduce(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.selecteIntroduce(userDetails.getUser().getId());
    }

    @PutMapping("/password")
    public SignupResponseDto editPassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody EditPasswordRequestDto requestDto){
        return  userService.editPassword(userDetails.getUser(),requestDto);
    }

}