package com.sparta.newsfeed.Comment.entity;

import com.sparta.newsfeed.Comment.dto.CommentRequestDto;
import com.sparta.newsfeed.Feed.entity.Feed;
import com.sparta.newsfeed.User.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comments;

    @JoinColumn(nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;

    public Comment(CommentRequestDto requestDto, User user, Feed feed) {
        this.comments = requestDto.getComments();
        this.username = user.getUsername();
        this.user = user;
        this.feed = feed;
    }

    public void update(CommentRequestDto requestDto) {
        this. comments = requestDto.getComments();
    }
}
