package com.sparta.newsfeed.Feed.entity;

import com.sparta.newsfeed.Comment.entity.Comment;
import com.sparta.newsfeed.Feed.dto.FeedRequestDto;
import com.sparta.newsfeed.FeedFolder.entity.FeedFolder;
import com.sparta.newsfeed.Like.entity.FeedLike;
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

    @Column
    private Long likeCount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "feed")
    private List<FeedFolder> feedFolderList = new ArrayList<>();

    @OneToMany(mappedBy = "feed")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feed", cascade = CascadeType.REMOVE)
    private List<FeedLike> feedLikeList = new ArrayList<>();

    public Feed(FeedRequestDto requestDto, User user) {
        if (requestDto.getTitle() == null) { // 제목을 입력 안하면 url이 출력되도록
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

    public void mappingFeedLike(FeedLike feedLike) { // 좋아요 수를 세기 위해 추가
        this.feedLikeList.add(feedLike);
    }

    public void updateLikeCount() { // 피드 내 좋아요 수 확인은 따로 변수를 생성하지 않고 목록의 크기로 확인
        this.likeCount = (long)this.feedLikeList.size();
    }

    public void subLikeCount(FeedLike feedLike) { // 좋아요 목록에서 삭제
        this.feedLikeList.remove(feedLike);
    }
}
