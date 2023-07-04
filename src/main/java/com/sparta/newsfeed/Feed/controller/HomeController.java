package com.sparta.newsfeed.Feed.controller;

import com.sparta.newsfeed.Feed.dto.FeedResponseDto;
import com.sparta.newsfeed.Feed.entity.Feed;
import com.sparta.newsfeed.Feed.service.FeedService;
import com.sparta.newsfeed.User.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class HomeController {

    private final FeedService feedService;

    @GetMapping("/")
    public String getFeeds(Model model) {
        List<FeedResponseDto> feeds = feedService.getFeeds();
        model.addAttribute("feeds",feeds);
        return "index";
    }
    @GetMapping("newsfeed/feeds/{user_id}")
    public String getFeedListsByUser(@PathVariable Long user_id, Model model) {
        List<FeedResponseDto> feed = feedService.getFeedListsByUser(user_id);
        model.addAttribute("feed", feed);
        return "feedList";
    }

    @GetMapping("newsfeed/newFeed")
    public String getFeed(Model model) {
        model.addAttribute("feed",new FeedResponseDto());
        return "feed";
    }
    @GetMapping("newsfeed/newFeed/{feed_id}")
    public String getFeedByFeedId(@PathVariable Long feed_id, Model model) {
        FeedResponseDto feed = feedService.getFeed(feed_id);
        model.addAttribute("feed", feed);
        return "feed";
    }
}
