package com.sparta.newsfeed.Like.repository;

import com.sparta.newsfeed.Feed.entity.Feed;
import com.sparta.newsfeed.Like.entity.Like;
import com.sparta.newsfeed.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Object> findByUserAndFeed(User user, Feed feed);
}
