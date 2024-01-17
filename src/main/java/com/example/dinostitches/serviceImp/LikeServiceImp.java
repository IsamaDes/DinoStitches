package com.example.dinostitches.serviceImp;

import com.example.dinostitches.model.Comment;
import com.example.dinostitches.model.Like;
import com.example.dinostitches.model.Post;
import com.example.dinostitches.model.Users;
import com.example.dinostitches.repository.LikeRepository;
import com.example.dinostitches.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImp {
    private PostRepository postRepository;
    private UserServiceImpl userService;
    private LikeRepository likeRepository;
    private CommentServiceImp commentService;

    @Autowired
    public LikeServiceImp(PostRepository postRepository, UserServiceImpl userService, LikeRepository likeRepository, CommentServiceImp commentService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.likeRepository = likeRepository;
        this.commentService  = commentService;
    }

    public ResponseEntity<Post> increaseLikePostById(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);

        if (post != null) {
            post.incrementPostLikedCount();
            postRepository.save(post);

            return new ResponseEntity<>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<Post> decreaseLikePostById(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            post.decrementPostLikedCount();
            postRepository.save(post);

            return new ResponseEntity<>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public Like likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("post not found: " + postId));
        Users user = userService.findUserById(userId);

        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();
        return likeRepository.save(like);
    }

    public Like likeComment (Long commentId, Long userId){
        Comment comment = commentService.getCommentById(commentId);
        Users user = userService.findUserById(userId);

        Like like = Like.builder()
                .user(user)
                .comment(comment)
                .build();
        return likeRepository.save(like);
    }
}
