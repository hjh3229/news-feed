package com.sparta.newsfeed.User.dto;

import lombok.Getter;

@Getter

public class CheckPasswordResponseDto {

    boolean checkPassword;

    public CheckPasswordResponseDto(boolean checkPassword) {
        this.checkPassword = checkPassword;
    }
}
