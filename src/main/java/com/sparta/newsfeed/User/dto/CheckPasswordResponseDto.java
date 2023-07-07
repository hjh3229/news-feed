package com.sparta.newsfeed.User.dto;

import lombok.Getter;

@Getter

public class CheckPasswordResponseDto {

    private Integer statusCode;


    public CheckPasswordResponseDto(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
