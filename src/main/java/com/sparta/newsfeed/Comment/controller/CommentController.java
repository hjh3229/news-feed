package com.sparta.newsfeed.Comment.controller;

import com.sparta.newsfeed.Comment.dto.CommentRequestDto;
import com.sparta.newsfeed.Comment.service.CommentService;
import com.sparta.newsfeed.Common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/newsfeed")
public class CommentController { // 코멘트 조회는 FeedResponse에서 대체

    private final CommentService commentService;

    @PostMapping("/comment/{blog_id}") // 입력한 blog id에 코멘트 추가
    public void createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto, @PathVariable Long blog_id) {
        commentService.createComment(userDetails.getUser(), requestDto, blog_id);
    }

    @PutMapping("/comment/{comment_id}")
    public void updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long comment_id, @RequestBody CommentRequestDto requestDto) {
        commentService.updateComment(userDetails.getUser(), comment_id, requestDto);
    }

    @DeleteMapping("/comment/{comment_id}") // 해당 코멘트 삭제
    public ResponseEntity<String> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long comment_id) {
        commentService.deleteComment(userDetails.getUser(), comment_id);
        return ResponseEntity.status(HttpStatus.OK).body("댓글 삭제 성공"); // body에 댓글 삭제 성공 메시지 반환
    }
}
