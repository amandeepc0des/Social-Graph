package com.socialgraph.analyzer.repository;

import com.socialgraph.analyzer.entity.Notification;
import com.socialgraph.analyzer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> getNotificationsByRecipientOrderByCreatedAtDesc(User user);
}
