package com.sparta.newsfeed.Comment.dto;

import com.sparta.newsfeed.Comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private Long id;
    private Long blog_id;
    private String username;
    private String comments;
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.blog_id = comment.getFeed().getId();
        this.username = comment.getUsername();
        this.comments = comment.getComments();
    }
}
