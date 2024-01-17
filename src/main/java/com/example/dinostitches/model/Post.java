package com.example.dinostitches.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    @Data
    @Table(name="posts")
    public class Post {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_generator")
        private long id;

        @NotBlank
        @Column(name="post_title", unique = true)
        private String title;

        @NotBlank
        @Column(name="post_content", unique = true)
        private String content;

        @NotBlank
        @Column(name="description", length = 2000)
        private String description;

        @ManyToOne(fetch = FetchType.EAGER, optional = false)
        @NotBlank
        @JoinColumn(name = "user_id", nullable = false)
        private Users user;

        @JsonIgnore
        @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
        private List<Like> like;

        @JsonIgnore
        @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
        private List<Comment> comment;

        @Column(name="published")
        private boolean published;

        @Column(name="postDate")
        @Temporal(TemporalType.TIMESTAMP)
        @org.hibernate.annotations.CreationTimestamp
        private Date postDate;

        @Column(name="image")
        private String image;

        @Column(name="likes")
        private Integer likes=0;



        public Post(String title, String description ){
            this.title = title;
            this.description= description;

        }

/////////////////editByTitle getmethod/////////////
        public Post get() {
            return null;
        }

        public Integer incrementPostLikedCount(){
            likes++;
            return likes;

        }

        public Integer decrementPostLikedCount(){
            if(likes==0){
                return 0;
            } else {
                likes--;
            }
            return likes;
        }
    }

