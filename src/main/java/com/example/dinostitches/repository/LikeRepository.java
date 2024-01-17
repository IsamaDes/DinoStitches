package com.example.dinostitches.repository;


import com.example.dinostitches.model.Like;
import com.example.dinostitches.model.Post;
import com.example.dinostitches.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(Post post, Users users);
}
