package com.sparta.newsfeed.Comment.repository;

import com.sparta.newsfeed.Comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
