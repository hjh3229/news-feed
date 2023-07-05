package com.sparta.newsfeed.Like.entity;

import com.sparta.newsfeed.Feed.entity.Feed;
import com.sparta.newsfeed.User.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FeedLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;

    public FeedLike(User user, Feed feed) {
        this.user = user;
        this.feed = feed;
    }

    public static boolean isLikedFeed(Optional<FeedLike> optionalLike) { // 좋아요 여부 반환
        return optionalLike.isPresent();
    }

    public void mappingUser(User user) {
        this.user = user;
        user.mappingLike(this);
    }

    public void mappingFeed(Feed feed) {
        this.feed = feed;
        feed.mappingFeedLike(this);
    }
}
