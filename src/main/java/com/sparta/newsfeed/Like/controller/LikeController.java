package com.sparta.newsfeed.Like.controller;

import com.sparta.newsfeed.Like.dto.LikeRequestDto;
import com.sparta.newsfeed.Like.entity.Like;
import com.sparta.newsfeed.Like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/newsfeed")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like")
    public void like(@RequestBody LikeRequestDto requestDto) {
        likeService.like(requestDto);
    }

    @DeleteMapping("/like")
    public void cancel(@RequestBody LikeRequestDto requestDto) {
        likeService.cancel(requestDto);
    }
}
