package com.sparta.newsfeed.Like.dto;

import com.sparta.newsfeed.Like.controller.LikeController;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeRequestDto {

    private Long user_id;
    private Long feed_id;

    public LikeRequestDto(Long user_id, Long feed_id) {
        this.user_id = user_id;
        this.feed_id = feed_id;
    }
}
