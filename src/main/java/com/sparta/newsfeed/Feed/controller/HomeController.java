package com.sparta.newsfeed.Feed.controller;

import com.sparta.newsfeed.Feed.dto.FeedResponseDto;
import com.sparta.newsfeed.Feed.entity.Feed;
import com.sparta.newsfeed.Feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public String getFeedsByUser(@PathVariable Long user_id, Model model) {
        List<FeedResponseDto> feed = feedService.getFeedsByUser(user_id);
        model.addAttribute("feed", feed);
        return "feedList";
    }

}
