package com.sparta.newsfeed.Feed.Feed.entity;

import com.sparta.newsfeed.Feed.dto.FeedRequestDto;
import com.sparta.newsfeed.User.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(nullable = false)
    private String url;

    @Column
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private  User user;

    public Feed(FeedRequestDto feedRequestDto, User user) {
        this.title = feedRequestDto.getTitle();
        this.url = feedRequestDto.getUrl();
        this.contents = feedRequestDto.getContents();
        this.user = user;
    }
}
