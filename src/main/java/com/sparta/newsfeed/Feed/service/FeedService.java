package com.sparta.newsfeed.Feed.service;

import com.sparta.newsfeed.Feed.dto.FeedRequestDto;
import com.sparta.newsfeed.Feed.dto.FeedResponseDto;
import com.sparta.newsfeed.Feed.entity.Feed;
import com.sparta.newsfeed.Feed.repository.FeedRepository;
import com.sparta.newsfeed.FeedFolder.entity.FeedFolder;
import com.sparta.newsfeed.FeedFolder.repository.FeedFolderRepository;
import com.sparta.newsfeed.Folder.entity.Folder;
import com.sparta.newsfeed.Folder.repository.FolderRepository;
import com.sparta.newsfeed.Like.entity.FeedLike;
import com.sparta.newsfeed.Like.repository.LikeRepository;
import com.sparta.newsfeed.User.entity.User;
import com.sparta.newsfeed.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final FeedFolderRepository feedFolderRepository;
    private final LikeRepository likeRepository;

    public void create(User user, FeedRequestDto requestDto) {
        feedRepository.save(new Feed(requestDto,user));
    }

    @Transactional(readOnly = true)
    public List<FeedResponseDto> getFeeds() {
        return feedRepository.findAll().stream().map(FeedResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public List<FeedResponseDto> getFeedsByUser(Long userId) {
        return feedRepository.findAllByUserId(userId).stream().map(FeedResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public List<FeedResponseDto> getFeedsByFolder(Long folderId, User user) {
        List<Feed> feedList = feedRepository.findAllByUserAndFeedFolderList_FolderId(user, folderId);
        List<FeedResponseDto> responseDtoList = new ArrayList<>();
        for (Feed feed : feedList) {
            responseDtoList.add(new FeedResponseDto(feed));
        }
        return responseDtoList;
    }
  
      public FeedResponseDto getFeed(Long feed_id) {
        Feed feed = feedRepository.findById(feed_id).orElseThrow(
                ()->new NullPointerException("not found Feed")
        );
        return new FeedResponseDto(feed);
    }

    @Transactional
    public void updateFeed(FeedRequestDto requestDto, Long id, User user) {
        Feed feed = feedRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 피드는 존재하지 않습니다.")
        );
        if (feed.getUser().getUsername().equals(user.getUsername())) {
            feed.update(requestDto);
        } else {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }

    public void deleteFeed(Long id, User user) {
        Feed feed = feedRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 피드는 존재하지 않습니다.")
        );
        if (feed.getUser().getUsername().equals(user.getUsername())) {
            feedRepository.delete(feed);
        } else {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }

    // 폴더 추가
    public void addFolder(Long feedId, Long folderId, User user){
        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new IllegalArgumentException("해당 피드가 존재하지 않습니다"));

        Folder folder = folderRepository.findById(folderId).orElseThrow(
                () -> new IllegalArgumentException("해당 폴더가 존재하지 않습니다"));

        if(!feed.getUser().getId().equals(user.getId())
        || !folder.getUser().getId().equals(user.getId())){
            throw new IllegalArgumentException("회원님의 피드가 아니거나, 회원님의 폴더가 아닙니다. ");
        }

        Optional<FeedFolder> overlapFolder = feedFolderRepository.findByFeedAndFolder(feed,folder);

        if(overlapFolder.isPresent()){
            throw new IllegalArgumentException("중복된 촐더입니다.");
        }
        feedFolderRepository.save(new FeedFolder(feed,folder));
    }

    public void like(Long feedId, Long userId) {
        Feed feed = feedRepository.findById(feedId).orElseThrow(() ->
                new IllegalArgumentException("해당 피드가 존재하지 않습니다.")
        );
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("로그인 시 이용 가능합니다.")
        );
        Optional<FeedLike> isLike = likeRepository.findByUserAndFeed(user, feed);

        isLike.ifPresentOrElse(
                like -> { // 게시물과 유저를 통해 좋아요를 이미 누른게 확인이 되면 삭제
                    likeRepository.delete(like);
                    feed.subLikeCount(like);
                },
                () -> { // 좋아요를 아직 누르지 않았을 땐 추가
                    FeedLike like = new FeedLike(user, feed);

                    like.mappingFeed(feed);
                    like.mappingUser(user);
                    feed.updateLikeCount();

                    likeRepository.save(like);
                }
        );
    }

    public boolean isLiked(Long feedId, Long userId) {
        Feed feed = feedRepository.findById(feedId).orElseThrow(() ->
                new IllegalArgumentException("해당 피드가 존재하지 않습니다.")
        );
//        User user = userRepository.findById(userId).orElseThrow(() ->
//                new IllegalArgumentException("로그인 시 이용 가능합니다.")
//        );
        // 에러를 무시하는 방법
        User user = userRepository.findById(userId).orElse(new User());
        Optional<FeedLike> isLike = likeRepository.findByUserAndFeed(user, feed);
        boolean isLiked = FeedLike.isLikedFeed(isLike);
        return isLiked;
    }
}
