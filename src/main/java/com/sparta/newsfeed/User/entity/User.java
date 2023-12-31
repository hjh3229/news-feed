package com.sparta.newsfeed.User.entity;

import com.sparta.newsfeed.Feed.entity.Feed;
import com.sparta.newsfeed.Like.entity.FeedLike;
import com.sparta.newsfeed.User.dto.IntroduceRequestDto;
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
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column()
    private String nickname;

    @Column()
    private String my_content;

    @OneToMany(mappedBy = "user")
    private List<Feed> feeds = new ArrayList(); // 양방향성을 위해 추가 (한지훈)

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<FeedLike> feedLikes = new ArrayList<>();
  

    public User(String username, String password,String email, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public void update(IntroduceRequestDto requestDto) {
        this.nickname= requestDto.getNickname();
        this.my_content= requestDto.getMy_content();
    }

    public void updatePassword(String password) {
        this.password=password;
    }

    public void mappingLike(FeedLike feedLike) { // 유저가 해당 좋아요를 눌렀는지 확인
        this.feedLikes.add(feedLike);
    }
}
