package com.sparta.newsfeed.Feed.dto;

import com.sparta.newsfeed.Feed.entity.Feed;
import lombok.Getter;

@Getter
public class FeedResponseDto {
    private Long id;
    private String title;
    private String msg;

    public FeedResponseDto(Feed newfeed, String feedSuccess) {
        this.title = newfeed.getTitle();
        this.msg = feedSuccess;
    }
}
