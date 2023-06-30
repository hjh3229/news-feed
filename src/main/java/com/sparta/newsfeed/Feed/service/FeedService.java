package com.sparta.newsfeed.Feed.service;

import com.sparta.newsfeed.Feed.dto.FeedRequestDto;
import com.sparta.newsfeed.Feed.dto.FeedResponseDto;
import com.sparta.newsfeed.Feed.entity.Feed;
import com.sparta.newsfeed.Feed.repository.FeedRepository;
import com.sparta.newsfeed.User.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;

    public FeedResponseDto create(FeedRequestDto requestDto, User user) {
        Feed newfeed = feedRepository.save(new Feed(requestDto,user));
        return new FeedResponseDto(newfeed,"Feed Success");
    }
}
