package com.sparta.newsfeed.Feed.Feed.repository;

import com.sparta.newsfeed.Feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed,Long> {
}
