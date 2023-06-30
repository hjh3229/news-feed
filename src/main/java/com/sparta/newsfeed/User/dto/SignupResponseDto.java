package com.sparta.newsfeed.User.dto;

public class SignupResponseDto {

    private String username;

    private String msg;


    public SignupResponseDto(String username, String msg) {
        this.username = username;
        this.msg = msg;
    }
}
