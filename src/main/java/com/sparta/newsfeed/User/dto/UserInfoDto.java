package com.sparta.newsfeed.User.dto;

import com.sparta.newsfeed.Common.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    String username;
    String nickname;
    String my_content;

    public UserInfoDto(UserDetailsImpl userDetails) {
        this.username=userDetails.getUsername();
        this.nickname=userDetails.getNickname();
        this.my_content=userDetails.getMy_content();

    }
}