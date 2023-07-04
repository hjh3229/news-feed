package com.sparta.newsfeed.Feed.service;

import com.sparta.newsfeed.Feed.dto.FeedRequestDto;
import com.sparta.newsfeed.Feed.dto.FeedResponseDto;
import com.sparta.newsfeed.Feed.entity.Feed;
import com.sparta.newsfeed.Feed.repository.FeedRepository;
import com.sparta.newsfeed.FeedFolder.entity.FeedFolder;
import com.sparta.newsfeed.FeedFolder.repository.FeedFolderRepository;
import com.sparta.newsfeed.Folder.entity.Folder;
import com.sparta.newsfeed.Folder.repository.FolderRepository;
import com.sparta.newsfeed.User.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final FolderRepository folderRepository;
    private final FeedFolderRepository feedFolderRepository;

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



//    @Transactional(readOnly = true)
//    public List<FeedResponseDto> getFeedsByFolder(Long folderId) {
//        return feedRepository.findAllByFolderId(folderId).stream().map(FeedResponseDto::new).toList();
//    }

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

    public Page<FeedResponseDto> getFeedInFolder(Long folderId, int page, int size, String sortBy, boolean isAsc, User user){

        // 페이징 정렬
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Feed> feedList = feedRepository.findAllByUserAndFeedFolderList_FolderId(user, folderId, pageable);
        Page<FeedResponseDto> responseDtoList = feedList.map(FeedResponseDto::new);

        return responseDtoList;
    }

}
