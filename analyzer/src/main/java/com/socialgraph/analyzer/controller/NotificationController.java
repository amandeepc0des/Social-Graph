package com.socialgraph.analyzer.controller;

import com.socialgraph.analyzer.entity.Notification;
import com.socialgraph.analyzer.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService)
    {
        this.notificationService = notificationService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable Long userId)
    {
        return ResponseEntity.ok(notificationService.getUserNotifications(userId));
    }

    @PutMapping("/read/{id}")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable Long id)
    {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    @GetMapping("/unReadCount/{userId}")
    public ResponseEntity<String> getUnReadCount(@PathVariable Long userId)
    {
        return ResponseEntity.ok("Unread notification Count is: " + notificationService.getUnReadCount(userId));
    }

}
