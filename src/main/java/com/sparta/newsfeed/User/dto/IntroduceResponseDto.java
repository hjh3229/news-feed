package com.sparta.newsfeed.User.dto;

import com.sparta.newsfeed.User.entity.User;

public class IntroduceResponseDto {
    private String nickname;
    private String my_content;


    public IntroduceResponseDto(User user) {
        this.nickname=user.getNickname();
        this.my_content=user.getMy_content();
    }
}
