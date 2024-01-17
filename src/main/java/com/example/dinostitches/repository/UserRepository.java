package com.example.dinostitches.repository;

import com.example.dinostitches.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {



    Optional<UserDetails> findByUsername(String username);

    Optional<Users> findUserById(Long userId);


    boolean existsByUsername(String username);

    void deleteById(Long userId);

}

