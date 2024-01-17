package com.example.dinostitches.controller;


import com.example.dinostitches.model.Like;
import com.example.dinostitches.model.Post;
import com.example.dinostitches.serviceImp.CommentServiceImp;
import com.example.dinostitches.serviceImp.LikeServiceImp;
import com.example.dinostitches.serviceImp.PostServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@PreAuthorize("hasRole = ('User')")
@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {
    private LikeServiceImp likeService;
    private PostServiceImp postService;
    private CommentServiceImp commentService;
    @Autowired
    public  LikeController(LikeServiceImp likeService, PostServiceImp postService, CommentServiceImp commentService){

        this.likeService=likeService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @PutMapping("/like-post/{postId}")
    public ResponseEntity<Post> getIncreaseLikeById(@PathVariable Long postId){
        return likeService.increaseLikePostById(postId);
    }

    @PutMapping("/unlike-post/{postId}")
    public ResponseEntity<Post> getDecreaseLikeById(@PathVariable Long postId){
        return likeService.decreaseLikePostById(postId);
    }


    @PostMapping ("/like-post/{postId}/user/{userId}")
    public  ResponseEntity<Like> likePost(@PathVariable Long postId, @PathVariable Long userId){
        Like postLike = likeService.likePost(postId, userId);
        return  new ResponseEntity<>(postLike, HttpStatus.CREATED);
    }

    @PostMapping("/like-comment/{commentId}/user/{userId}")
    public ResponseEntity<Like> likeComment(@PathVariable Long commentId, @PathVariable Long userId){
        Like commentLike = likeService.likeComment(commentId, userId);
        return new ResponseEntity<>(commentLike, HttpStatus.CREATED);

    }
}
