package com.sparta.newsfeed.Comment.service;

import com.sparta.newsfeed.Comment.dto.CommentRequestDto;
import com.sparta.newsfeed.Comment.dto.CommentResponseDto;
import com.sparta.newsfeed.Comment.entity.Comment;
import com.sparta.newsfeed.Comment.repository.CommentRepository;
import com.sparta.newsfeed.Feed.entity.Feed;
import com.sparta.newsfeed.Feed.repository.FeedRepository;
import com.sparta.newsfeed.User.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final FeedRepository feedRepository;

    public void createComment(User user, CommentRequestDto requestDto, Long blog_id) {
        Feed feed = feedRepository.findById(blog_id).orElseThrow(() ->
                new RuntimeException("해당 피드를 찾을 수 없습니다.")
        );
        Comment comment = commentRepository.save(new Comment(requestDto, user, feed));
        new CommentResponseDto(comment);
    }

    @Transactional
    public void updateComment(User user, Long id, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new RuntimeException("없는 댓글입니다.")
        );
        if (user.getUsername().equals(comment.getUsername())) {
            comment.update(requestDto);
        }
    }

    public void deleteComment(User user, Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new RuntimeException("없는 댓글입니다.")
        );
        if (user.getUsername().equals(comment.getUsername())) {
            commentRepository.delete(comment);
        }
    }
}
