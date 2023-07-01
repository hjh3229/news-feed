package com.sparta.newsfeed.Feed.controller;

import com.sparta.newsfeed.Common.security.UserDetailsImpl;
import com.sparta.newsfeed.Feed.dto.FeedRequestDto;
import com.sparta.newsfeed.Feed.dto.FeedResponseDto;
import com.sparta.newsfeed.Feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/newsfeed")
public class FeedController {

    private final FeedService feedService;

//    @PostMapping("/feed/{user_id}")
//    public FeedResponseDto create(@AuthenticationPrincipal UserDetailImpl userdetail, @RequestBody FeedRequestDto requestDto){
//        return feedService.create(userdetail,requestDto);
//    }

//    @GetMapping("/feeds/{user_id}")
//    public List<FeedResponseDto> getFeedsByUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//
//    }
}
