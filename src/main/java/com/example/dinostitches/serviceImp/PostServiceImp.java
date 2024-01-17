package com.example.dinostitches.serviceImp;


import com.example.dinostitches.dto.EditPostRequestDto;
import com.example.dinostitches.dto.PostRequestDto;
import com.example.dinostitches.model.Post;
import com.example.dinostitches.model.Users;
import com.example.dinostitches.repository.PostRepository;
import com.example.dinostitches.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;




@Service
    public class PostServiceImp {

        private PostRepository postRepository;
        private UserRepository userRepository;

        @Autowired
        public  PostServiceImp(PostRepository postRepository, UserRepository userRepository){
            this.postRepository= postRepository;
            this.userRepository= userRepository;
        }

//            public ResponseEntity<Post> savePost(Post newPost){
//            Post post = new Post(newPost.getTitle(), newPost.getDescription(), newPost.isPublished(), newPost.getImage(), newPost.getLikes());
//            postRepository.save(post);
//            return new ResponseEntity<>(post, HttpStatus.CREATED);
//        }


        public ResponseEntity<Post> savePost(PostRequestDto postRequestDto, Long userId){
            Users user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User with userId: " + userId));

            Post post = new Post();
            post.setTitle(postRequestDto.getTitle());
            post.setContent(postRequestDto.getContent());
            post.setUser(user);

            //save post in the repository
           postRepository.save(post);

            //return response entity
            return new ResponseEntity<>(post, HttpStatus.CREATED);
        }

        public ResponseEntity<List<Post>> getAllPost() {
            List<Post> allPost = postRepository.findAll();
            return new ResponseEntity<>(allPost, HttpStatus.FOUND);
        }

        public ResponseEntity<List<Post>> getPostByUserId(Long userId, Long postId) {
            List<Post> userPost = postRepository.findPostById(postId);
            if (userPost.equals(userId)){

            }

            return ResponseEntity.ok(userPost);
        }

        public ResponseEntity <Post> findPostById(Long postId){
            //retrieve the post entity from the repository using the postId
            Post post = postRepository.findById(postId).orElseThrow(()->new RuntimeException("No post found with postId: " + postId));
            // create a response entity with the retrieved post and Http Status found
            return new ResponseEntity<>(post, HttpStatus.FOUND);

        }

        public ResponseEntity<List<Post>> searchByPostTitle(String title) {
            List<Post> allSearchedPost = postRepository.findByTitleContaining(title);
            if (allSearchedPost.isEmpty()) {
                return new ResponseEntity<>(allSearchedPost, HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(allSearchedPost, HttpStatus.FOUND);
            }
        }

        public ResponseEntity<Void> deletePostById(Long id){
            postRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        public ResponseEntity<Post> editPostById(Long id, Post postToBeEdited) {
            Optional<Post> post = postRepository.findById(id);
            if (post.isPresent()) {
                Post post1 = post.get();
                post1.setTitle(postToBeEdited.getTitle());
                post1.setDescription(postToBeEdited.getDescription());
                postRepository.save(post1);
                return new ResponseEntity<>(post1, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

            public ResponseEntity<Post> editPostByTitle(String title, Post postToBeEdited){
            Optional<Post> postTobeEdited = postRepository.findByTitle(title);

            if(postTobeEdited.isPresent()){
                Post post1= postToBeEdited.get();
                post1.setTitle(postToBeEdited.getTitle());
                post1.setDescription(postToBeEdited.getDescription());
                postRepository.save(post1);
                return  new ResponseEntity<>(post1, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
         }


         public ResponseEntity<String> editPostContent(EditPostRequestDto editPostReQuestDto, Long userId, Long postId ){
            Post optionalUser = this.postRepository.findById(userId).orElseThrow(()->new NullPointerException("User with userId: " + userId + "not found in the database"));
            Post optionalPost = this.postRepository.findById(postId).orElseThrow(()->new NullPointerException("Post with postId: " + postId +  "not found in the database"));

          if(!optionalPost.getUser().equals (optionalUser)){
              throw  new RuntimeException("you cannot edit this post because you were not the one that posted it");
          }
          optionalPost.setDescription(editPostReQuestDto.getContent());
          this.postRepository.save(optionalPost);

          return new ResponseEntity<>("Post succesfully editted", HttpStatus.OK);


        }
    }


