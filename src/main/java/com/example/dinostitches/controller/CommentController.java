package com.example.dinostitches.controller;


import com.example.dinostitches.dto.CommentRequestDto;
import com.example.dinostitches.model.Comment;
import com.example.dinostitches.serviceImp.CommentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("*")
@PreAuthorize("hasRole = ('User')")
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    private CommentServiceImp commentService;

    @Autowired
    public CommentController(CommentServiceImp commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/save-comment/{postId}/{userId}")
    public ResponseEntity<Comment> createCommentByPostId(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, @PathVariable Long userId) {
        return commentService.createCommentByPostId(postId, commentRequestDto, userId);
    }

    @PutMapping("/edit-comment/{commentId}")
    public ResponseEntity<Comment> editCommentById(@PathVariable Long commentId, @RequestBody Comment newComment) {
        return commentService.editCommentById(commentId, newComment);
    }

    @GetMapping("/all-comment/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentByPostId(postId);
    }
    @DeleteMapping("/delete-comment/{commentId}")
    public ResponseEntity<Void> deleteCommentByCommentId(@PathVariable Long commentId){
        return  commentService.deleteCommentById(commentId);
    }

    @GetMapping("/get-comment/{content}")
    public ResponseEntity<List<Comment>> searchCommentByContent(@PathVariable String content) {
        return commentService.searchCommentByContent(content);
    }


}


