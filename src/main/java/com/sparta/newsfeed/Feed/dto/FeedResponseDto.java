package com.sparta.newsfeed.Feed.dto;

import com.sparta.newsfeed.Feed.entity.Feed;
import com.sparta.newsfeed.FeedFolder.entity.FeedFolder;
import com.sparta.newsfeed.Folder.dto.FolderResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class FeedResponseDto {
    private Long id;
    private String title;
    private String url;
    private String contents;

    private List<FolderResponseDto> feedFolderList = new ArrayList<>();

    public FeedResponseDto(Feed feed) {
        this.id = feed.getId();
        this.title = feed.getTitle();
        this.url = feed.getUrl();
        this.contents = feed.getContents();
        for (FeedFolder feedFolder : feed.getFeedFolderList()) {
            feedFolderList.add(new FolderResponseDto(feedFolder.getFolder()));
        }
    }
}
