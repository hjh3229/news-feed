package com.sparta.newsfeed.Feed.controller;

import com.sparta.newsfeed.Common.security.UserDetailsImpl;
import com.sparta.newsfeed.Feed.dto.FeedRequestDto;
import com.sparta.newsfeed.Feed.dto.FeedResponseDto;
import com.sparta.newsfeed.Feed.service.FeedService;
import com.sparta.newsfeed.Folder.dto.FolderRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/newsfeed")
public class FeedController {

    private final FeedService feedService;

    @PostMapping("/feed")
    public void create(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody FeedRequestDto requestDto){
        feedService.create(userDetails.getUser(),requestDto);
    }



    @PutMapping("/feed/{id}")
    public String updateFeed(@RequestBody FeedRequestDto requestDto, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        feedService.updateFeed(requestDto, id, userDetails.getUser());
        return "feedList";
    }

    @DeleteMapping("/feed/{id}")
    public String deleteFeed(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        feedService.deleteFeed(id, userDetails.getUser());
        return "feedList";
    }
  
    // 폴더 추가
    @PostMapping("{feedId}/folder")
    public void addFolder(@PathVariable Long feedId,@RequestParam Long folderId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        feedService.addFolder(feedId,folderId,userDetails.getUser());
    }

    @PostMapping("/feed/{id}/like")
    public void like(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        feedService.like(id, userDetails.getUser().getId());
    }

    /* to 호중님
    로그인 상태라면 좋아요 여부를 반환하고, 아니라면 무조건 false를 반환하도록 짰습니다.
    근데 이 방식이 user를 찾지 못했을 경우 에러를 무시하고 false를 반환하는 형식이라서...
    혹시 이 방식이 에러가 발생하거나 user를 찾지 못했을 때 발생하는 에러의 무시가 좋지 않다고 생각하신다면
    프론트에서 이 api를 로그인 상태일 때만 불러오도록 수정하셔도 좋을 것 같습니다.
    좋아요를 누르면 ture
    */

    @GetMapping("/feed/{id}/like")
    public boolean isLiked(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return feedService.isLiked(id, userDetails.getUser().getId());
    }
   
}
