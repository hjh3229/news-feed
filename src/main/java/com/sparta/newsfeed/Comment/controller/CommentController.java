package com.sparta.newsfeed.Comment.controller;

import com.sparta.newsfeed.Comment.dto.CommentRequestDto;
import com.sparta.newsfeed.Comment.dto.CommentResponseDto;
import com.sparta.newsfeed.Comment.service.CommentService;
import com.sparta.newsfeed.Common.security.UserDetailsImpl;
import com.sparta.newsfeed.Folder.dto.FolderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/newsfeed")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public void createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto, @RequestBody Long blog_id) {
        commentService.createComment(userDetails.getUser(), requestDto, blog_id);
    }

    @PutMapping("/comment")
    @ResponseBody
    public void updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody Long id, @RequestBody CommentRequestDto requestDto) {
        commentService.updateComment(userDetails.getUser(), id, requestDto);
    }

    @DeleteMapping("/comment")
    public ResponseEntity<String> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Long id) {
        commentService.deleteComment(userDetails.getUser(), id);
        return ResponseEntity.status(HttpStatus.OK).body("댓글 삭제 성공");
    }
}
