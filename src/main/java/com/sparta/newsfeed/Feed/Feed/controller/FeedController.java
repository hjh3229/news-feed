package com.sparta.newsfeed.Feed.Feed.controller;

import com.sparta.newsfeed.Feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/newsfeed")
public class FeedController {

    private final FeedService feedService;

//    @PostMapping("newsfeed/feed/{user_id}")
//    public FeedResponseDto create(@AuthenticationPrincipal UserDetailImpl userdetail, @ModelAttribute FeedRequestDto requestDto){
//        return feedService.create(userdetail,requestDto);
//    }
}
