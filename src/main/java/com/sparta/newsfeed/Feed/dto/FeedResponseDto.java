package com.sparta.newsfeed.Feed.dto;

import com.sparta.newsfeed.Feed.entity.Feed;
import com.sparta.newsfeed.FeedFolder.entity.FeedFolder;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class FeedResponseDto {
    private Long id;
    private String title;
    private String url;
    private String contents;
//    private String msg;

//    private List<FolderResponseDto> feedFolderList = new ArrayList<>();

    public FeedResponseDto(Feed feed) {
        this.id = feed.getId();
        this.title = feed.getTitle();
        this.url = feed.getUrl();
        this.contents = feed.getContents();
//        this.msg = feedSuccess;
//        for (FeedFolder feedFolder : Feed.getFeedFolderList()) {
//            feedFolderList.add(new FolderResponseDto(feedFolder.getFolder()));
//        }
    }
}
