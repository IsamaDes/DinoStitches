package com.example.dinostitches.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;
//id-primarykey of the database

    //gv- id is automatically generated
    //sequence-type of sequence
    //lob-content field should be large object
    //many comment should be associated to one post
    //fetchtype=default value for many to one
    //join
    //joincolumn collects the postid from post to comment
    //this makes post a foregin key here
    //entity creates table,
    // @table allows us to give a name to our table different from the one the entity provides


@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor

@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="comment_generator")
    private Long id;

    @NotBlank
    @Column(name = "content", nullable=false, length = 2000)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @JsonIgnore
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Like> like;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Post post;


    @Column(name="commentDate")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date commentDate;


}
