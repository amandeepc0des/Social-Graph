package com.socialgraph.analyzer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "friendships", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"from_user_id", "to_user_id"})
})
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    User fromUserId;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    User toUserId;

    FriendshipStatus friendshipStatus;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
