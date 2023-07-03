package com.sparta.newsfeed.User.dto;

import com.sparta.newsfeed.User.entity.User;
import lombok.Getter;

@Getter
public class SignupResponseDto {

    private String username;

    private String msg;


    public SignupResponseDto(User user, String msg) {
        this.username = user.getUsername();
        this.msg = msg;
    }
}
