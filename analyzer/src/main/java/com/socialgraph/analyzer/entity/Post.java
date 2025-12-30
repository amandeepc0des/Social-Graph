package com.socialgraph.analyzer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String postContent;

    String postImageUrl;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Privacy privacy;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;


}
