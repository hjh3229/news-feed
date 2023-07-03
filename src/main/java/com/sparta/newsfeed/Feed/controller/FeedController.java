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

    @PostMapping("/feed/{user_id}")
    public FeedResponseDto create(@AuthenticationPrincipal UserDetailsImpl userdetail, @RequestBody FeedRequestDto requestDto){
        return feedService.create(userdetail.getUser(),requestDto);
    }

    @GetMapping("/feeds/{user_id}")
    public List<FeedResponseDto> getFeedsByUser(@PathVariable Long user_id) {
        return feedService.getFeedsByUser(user_id);
    }

    @GetMapping("/feeds/{folder_id}")
    public List<FeedResponseDto> getFeedsByFolder(@PathVariable Long folder_id) {
        return feedService.getFeedsByFolder(folder_id);
    }

    @PutMapping("/feed")
    public FeedResponseDto updateFeed(@RequestBody FeedRequestDto requestDto, @RequestBody Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return feedService.updateFeed(requestDto, id, userDetails.getUser());
    }

    @DeleteMapping("/feed")
    public void deleteFeed(@RequestBody Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        feedService.deleteFeed(id, userDetails.getUser());
    }
}
