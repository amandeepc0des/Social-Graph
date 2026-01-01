package com.socialgraph.analyzer.service;

import com.socialgraph.analyzer.entity.Notification;
import com.socialgraph.analyzer.entity.NotificationType;
import com.socialgraph.analyzer.entity.User;
import com.socialgraph.analyzer.exception.NotificationNotFoundException;
import com.socialgraph.analyzer.exception.UserNotFoundException;
import com.socialgraph.analyzer.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;


    public NotificationService(NotificationRepository notificationRepository, UserService userService)
    {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    @Transactional
    public void CreateNotification(Long recipientId, NotificationType type, Long relatedUserId, String message)
    {
        User user = userService.getUserById(recipientId);
        if(user == null) throw new UserNotFoundException("User with id: " + recipientId + " Not found", HttpStatus.NOT_FOUND);
        Notification notification = new Notification();
        notification.setCreatedAt(LocalDateTime.now());
        notification.setMessage(message);
        notification.setRecipient(user);
        notification.setIsRead(false);
        notification.setType(type);
        notification.setRelatedUserId(relatedUserId);
        notificationRepository.save(notification);
    }

    public List<Notification> getUserNotifications(Long userId)
    {
        User user = userService.getUserById(userId);
        return notificationRepository.getNotificationsByRecipientOrderByCreatedAtDesc(user);
    }

    @Transactional
    public String markAsRead(Long notificationId)
    {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(
                () -> new NotificationNotFoundException("This Notification doesn't exist", HttpStatus.NOT_FOUND)
        );
        notification.setIsRead(true);
        notificationRepository.save(notification);
        return "Notification is marked as read!";

    }

    public Long getUnReadCount(Long userId)
    {
        User user = userService.getUserById(userId);
        return notificationRepository.getNotificationsByRecipientOrderByCreatedAtDesc(user).stream().
                filter(notification -> notification.getIsRead().equals(false)).
                count();
    }
}
