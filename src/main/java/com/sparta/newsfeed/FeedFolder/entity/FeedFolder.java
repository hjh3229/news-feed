package com.sparta.newsfeed.FeedFolder.entity;

import com.sparta.newsfeed.Feed.entity.Feed;
import com.sparta.newsfeed.Folder.entity.Folder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="feed_folder")
public class FeedFolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // feed 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="feed_id",nullable = false)
    private Feed feed;

    // folder 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="folder_id",nullable = false)
    private Folder folder;

    public FeedFolder(Feed feed, Folder folder){
        this.feed = feed;
        this.folder = folder;
    }
}
