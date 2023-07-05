package com.sparta.newsfeed.FeedFolder.repository;

import com.sparta.newsfeed.Feed.entity.Feed;
import com.sparta.newsfeed.FeedFolder.entity.FeedFolder;
import com.sparta.newsfeed.Folder.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedFolderRepository extends JpaRepository<FeedFolder, Long>{

    Optional<FeedFolder> findByFeedAndFolder(Feed feed, Folder folder);
}

