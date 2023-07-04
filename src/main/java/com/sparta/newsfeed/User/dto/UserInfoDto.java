package com.sparta.newsfeed.User.dto;

import com.sparta.newsfeed.Common.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    String username;
    String nickname;
    String myContent;
}