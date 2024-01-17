package com.example.dinostitches.model;

import com.example.dinostitches.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users",
            uniqueConstraints = {
             @UniqueConstraint(columnNames = "username"),
             @UniqueConstraint(columnNames = "email")
            })
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user-seq", initialValue = 2000)
    @JsonIgnore
    private Long id;


    @Size(min = 3, max = 25, message = "Enter minimum of 3 characters and maximum of 25 characters in full name")
    private String username;

    @Size(min = 3, max = 25, message = "Enter minimum of 3 characters and maximum of 25 characters in full name")
    private String firstName;

    @Size(min = 3, max = 25, message = "Enter minimum of 3 characters and maximum of 25 characters in full name")
    private String lastName;

    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{8,}$ ",
            message = "Invalid password. It must contain at least 8 characters, including at least one digit, one lowercase letter, one uppercase letter, and one special character."
            )
    @Size(max = 100)
    @NotBlank
    private String password;

    @Email(message="Enter a valid email")
    @Size(max = 50)
    private String email;


    @Enumerated(value = EnumType.STRING)
    private Role userRole;


    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Post> post;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Comment> comment;


    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(Collections.singleton(new SimpleGrantedAuthority(this.userRole.name())));}

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
