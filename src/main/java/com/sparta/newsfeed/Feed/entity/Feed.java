package com.sparta.newsfeed.Feed.entity;

import com.sparta.newsfeed.Comment.entity.Comment;
import com.sparta.newsfeed.Feed.dto.FeedRequestDto;
import com.sparta.newsfeed.FeedFolder.entity.FeedFolder;
import com.sparta.newsfeed.User.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

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
    private User user;

    @OneToMany(mappedBy = "feed")
    private List<FeedFolder> feedFolderList = new ArrayList<>();

    @OneToMany(mappedBy = "feed")
    private List<Comment> commentList = new ArrayList<>();

    @ColumnDefault("0")
    @Column(name = "view_count",nullable = false)
    private Integer viewCount;

    public Feed(FeedRequestDto requestDto, User user) { // 피드 수정과 같은 요구사항을 받으므로 update도 생성자 사용
        if (requestDto.getTitle() == null) {
            this.title = requestDto.getUrl();
        } else {
            this.title = requestDto.getTitle();
        }
        this.url = requestDto.getUrl();
        this.contents = requestDto.getContents();
        this.user = user;
    }

    public void update(FeedRequestDto requestDto) {
        if (requestDto.getTitle() == null) {
            this.title = requestDto.getUrl();
        } else {
            this.title = requestDto.getTitle();
        }
        this.url = requestDto.getUrl();
        this.contents = requestDto.getContents();
    }
}
