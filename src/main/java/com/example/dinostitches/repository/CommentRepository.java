package com.example.dinostitches.repository;

import com.example.dinostitches.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);


        Comment getCommentById(Long id);


    List<Comment> findByContentIgnoreCaseContaining(String content);
}
