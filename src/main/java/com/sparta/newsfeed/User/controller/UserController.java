package com.sparta.newsfeed.User.controller;


import com.sparta.newsfeed.Common.security.UserDetailsImpl;
import com.sparta.newsfeed.User.service.UserService;
import org.springframework.stereotype.Controller;
import com.sparta.newsfeed.User.dto.*;
import com.sparta.newsfeed.Folder.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return userService.getUserInfo(userDetails);
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
    public SignupResponseDto editPassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody EditPasswordRequestDto requestDto){
        return  userService.editPassword(userDetails.getUser(),requestDto);
    }

    // folder model에 추가
    @GetMapping("/user-folder")
    public String getUserInfo(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        model.addAttribute("folder",folderService.getFolders(userDetails.getUser()));
        return null;
    }

}