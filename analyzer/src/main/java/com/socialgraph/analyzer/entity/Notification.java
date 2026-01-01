package com.socialgraph.analyzer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String message;
    Boolean isRead;
    LocalDateTime createdAt;
    NotificationType type;

    @ManyToOne
    @JoinColumn(name = "recipientId")
    User recipient;

    private Long relatedUserId;

}
