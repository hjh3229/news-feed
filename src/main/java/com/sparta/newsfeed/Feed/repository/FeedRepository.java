package com.sparta.newsfeed.Feed.repository;

import com.sparta.newsfeed.Feed.dto.FeedResponseDto;
import com.sparta.newsfeed.Feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FeedRepository extends JpaRepository<Feed,Long> {
    //List<Feed> findAllByOrderByModifiedAtDesc();
    List<Feed> findAllByUserId(Long userid);
    List<FeedResponseDto> findAllByFeedFolderList_FolderId(Long folderId);
}
