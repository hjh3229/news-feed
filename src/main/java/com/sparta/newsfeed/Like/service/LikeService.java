package com.sparta.newsfeed.Like.service;

import com.sparta.newsfeed.Feed.entity.Feed;
import com.sparta.newsfeed.Feed.repository.FeedRepository;
import com.sparta.newsfeed.Like.dto.LikeRequestDto;
import com.sparta.newsfeed.Like.entity.Like;
import com.sparta.newsfeed.Like.repository.LikeRepository;
import com.sparta.newsfeed.User.entity.User;
import com.sparta.newsfeed.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;

    public void like(LikeRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUser_id()).orElseThrow(() ->
                new IllegalArgumentException("로그인 시 이용 가능합니다.")
        );
        Feed feed = feedRepository.findById(requestDto.getFeed_id()).orElseThrow(() ->
                new IllegalArgumentException("없는 피드입니다.")
        );

        if (likeRepository.findByUserAndFeed(user, feed).isPresent()) {
            throw new RuntimeException("이미 좋아요 한 피드입니다.");
        }

        Like like = new Like();
    }

    public void cancel(LikeRequestDto requestDto) {
    }
}
