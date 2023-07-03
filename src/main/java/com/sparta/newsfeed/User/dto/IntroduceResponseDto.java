package com.sparta.newsfeed.User.dto;

public class IntroduceResponseDto {
    private String nickname;
    private String my_comment;

//    public IntroduceResponseDto(IntroduceRequestDto requestDto) {
//        this.nickname = requestDto.getNickname();
//        this.my_comment=requestDto.getMy_comment();
//    }

    public IntroduceResponseDto(String nickname, String myContent) {
        this.nickname=nickname;
        this.my_comment=myContent;
    }
}
