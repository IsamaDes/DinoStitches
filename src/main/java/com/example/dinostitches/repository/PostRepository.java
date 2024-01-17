package com.example.dinostitches.repository;


import com.example.dinostitches.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
        List<Post> findByTitleContaining(String title);
        Optional<Post> findByTitle(String title);


        List<Post> findPostById(Long postId);

        Optional<Post> findById(Long postId );

//        PostRequestDto save(String postRequestDto);

}


