package com.sparta.newsfeed.Feed.dto;

import com.sparta.newsfeed.Comment.dto.CommentResponseDto;
import com.sparta.newsfeed.Comment.entity.Comment;
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
    private Long userid;
    private String username;
    private List<CommentResponseDto> commentList; // Comment entity로 생성 시 순환 참조 문제가 발생

    private List<FolderResponseDto> feedFolderList = new ArrayList<>();

    private int likeCounts;

    public FeedResponseDto(Feed feed) {
        this.id = feed.getId();
        this.title = feed.getTitle();
        this.url = feed.getUrl();
        this.contents = feed.getContents();
        this.userid=feed.getUser().getId();
        this.username=feed.getUser().getUsername();
        for (FeedFolder feedFolder : feed.getFeedFolderList()) {
            feedFolderList.add(new FolderResponseDto(feedFolder.getFolder()));
        }
        this.commentList = new ArrayList<>();
        for (Comment comment : feed.getCommentList()) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            this.commentList.add(commentResponseDto);
        }
        this.likeCounts = feed.getFeedLikeList().size();
    }
}
