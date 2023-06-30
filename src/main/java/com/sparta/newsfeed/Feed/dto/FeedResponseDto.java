package com.sparta.newsfeed.Feed.dto;

import com.sparta.newsfeed.Feed.entity.Feed;

public class FeedResponseDto {

    private String title;
    private String msg;

    public FeedResponseDto(Feed newfeed, String feedSuccess) {
        this.title = newfeed.getTitle();
        this.msg = feedSuccess;
    }
}
