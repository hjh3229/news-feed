package com.sparta.newsfeed.User.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    String username;
    String nickname;
    String myContent;
    Long user_id;
}