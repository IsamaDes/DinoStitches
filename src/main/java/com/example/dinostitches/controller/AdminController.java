package com.example.dinostitches.controller;


import com.example.dinostitches.dto.PostRequestDto;
import com.example.dinostitches.model.Users;
import com.example.dinostitches.serviceImp.PostServiceImp;
import com.example.dinostitches.serviceImp.UserServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequestMapping("/api/v1/admin")
@SecurityRequirement(name = "Bearer Authentication")
@PreAuthorize("hasRole = ('Admin')")
@RestController
public class AdminController {
    private UserServiceImpl userService;
    private PostServiceImp postService;

    @Autowired
    public AdminController(UserServiceImpl userService, PostServiceImp postService){
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/admin-dashboard")
    @SecurityRequirement(name = "Bearer Authentication")
    public String index() {return "Welcome to Admin Dashboard";}

    @PostMapping("/ban-user/{userId}")
    public ResponseEntity<Void> banUser(@PathVariable Long userId){
        userService.banUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search-user/{userId}")
    public ResponseEntity<Users> searchForUser(@PathVariable Long userId){
        Users user = userService.findUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.FOUND);
    }

    @DeleteMapping("/delete-post/{postId}")
    public ResponseEntity<Void> deletePost(@RequestBody PostRequestDto postRequestDto, @PathVariable Long postId, @PathVariable Long userId) {
        return postService.deletePostById(postId);
    }

}
