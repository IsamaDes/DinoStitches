package com.example.dinostitches.serviceImp;


import com.example.dinostitches.dto.UserDto;
import com.example.dinostitches.enums.Role;
import com.example.dinostitches.model.Users;
import com.example.dinostitches.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

    @Service
    public class UserServiceImpl implements UserDetailsService {


        private UserRepository userRepository;
        private PasswordEncoder passwordEncoder;

        @Autowired
        public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }


        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        }

        public Function<UserDto, Users> saveUser = (userDto) -> {
            //Convert UserDto to User class
            Users user = new ObjectMapper().convertValue(userDto, Users.class);

            //before saving user check if the user username/email is already in the database
            if(userRepository.existsByUsername(user.getUsername())){
                ApiResponse apiResponse = new ApiResponse();
                try {
                    throw new BadRequestException(String.valueOf(apiResponse));
                } catch (BadRequestException e) {
                    throw new RuntimeException(e);
                }
            }
            //Encode users password
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            //assign a default role
            user.setUserRole(Role.ROLE_USER);

            //ToDo:set userId into database
            // user.setId(user.getId());
            return userRepository.save(user);
        };

        public void removeUser(Long userId) {
            userRepository.deleteById(userId);
        }

        public Users findUserById(Long userId) {
            return userRepository.findById(userId).orElseThrow(() -> new NullPointerException("user with userId: " + userId + "not found in the database"));
        }

        public void banUser(Long userId){
            Optional<Users> usersOptional = userRepository.findById(userId);
            Users user = usersOptional.orElseThrow(()->new RuntimeException("User with userId: " + userId + "not found"));
            userRepository.save(user);
        }



    }
