package com.sparta.newsfeed.Feed.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class FeedRequestDto {
    private String title;
    @NotBlank
    private String url;
    private String contents;
}
