package com.sparta.newsfeed.Feed.repository;

import com.sparta.newsfeed.Feed.dto.FeedResponseDto;
import com.sparta.newsfeed.Feed.entity.Feed;
import com.sparta.newsfeed.User.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface FeedRepository extends JpaRepository<Feed,Long> {
    List<Feed> findAllByOrderByModifiedAtDesc();
    List<Feed> findAllByUserId(Long userid);

    List<Feed> findAllByFolderId(Long folderId);

    Page<Feed> findAllByUserAndFeedFolderList_FolderId(User user, Long folderId, Pageable pageable);
}
