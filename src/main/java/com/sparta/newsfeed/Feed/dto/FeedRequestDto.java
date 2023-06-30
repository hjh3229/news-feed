package com.sparta.newsfeed.Feed.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class FeedRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String url;
    @NotBlank
    private String contents;
}
