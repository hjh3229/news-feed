package com.sparta.newsfeed.Feed.entity;

import com.sparta.newsfeed.Feed.dto.FeedRequestDto;
import com.sparta.newsfeed.FeedFolder.entity.FeedFolder;
import com.sparta.newsfeed.User.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private  User user;

    @OneToMany(mappedBy = "feed")
    private List<FeedFolder> feedFolderList = new ArrayList<>();

    public Feed(FeedRequestDto feedRequestDto, User user) { // 피드 수정과 같은 요구사항을 받으므로 update도 생성자 사용
        if (feedRequestDto.getTitle() == null) {
            this.title = feedRequestDto.getUrl();
        } else {
            this.title = feedRequestDto.getTitle();
        }
        this.url = feedRequestDto.getUrl();
        this.contents = feedRequestDto.getContents();
        this.user = user;
    }

    public void update(FeedRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.url = requestDto.getUrl();
        this.contents = requestDto.getContents();
    }
}
