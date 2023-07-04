package com.sparta.newsfeed.Feed.dto;

import com.sparta.newsfeed.Feed.entity.Feed;
import com.sparta.newsfeed.FeedFolder.entity.FeedFolder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
public class FeedResponseDto {
    private Long id;
    private String title;
    private String url;
    private String contents;
    private Long userid;
    private String username;

//    private List<FolderResponseDto> feedFolderList = new ArrayList<>();

    public FeedResponseDto(Feed feed) {
        this.id = feed.getId();
        this.title = feed.getTitle();
        this.url = feed.getUrl();
        this.contents = feed.getContents();
        this.userid=feed.getUser().getId();
        this.username=feed.getUser().getUsername();
//        for (FeedFolder feedFolder : Feed.getFeedFolderList()) {
//            feedFolderList.add(new FolderResponseDto(feedFolder.getFolder()));
//        }
    }
}
