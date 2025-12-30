package com.socialgraph.analyzer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    // here I am adding the @Id to make it the P.K and @GeneratedValue to autoincrement it.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @Column(nullable = false)
    String userName;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    String bio;
    String profilePictureUrl;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Boolean isActive;

}
