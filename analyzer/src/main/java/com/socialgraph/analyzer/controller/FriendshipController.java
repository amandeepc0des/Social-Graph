package com.socialgraph.analyzer.controller;

import com.socialgraph.analyzer.entity.Friendship;
import com.socialgraph.analyzer.entity.User;
import com.socialgraph.analyzer.service.FriendshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friendships")
public class FriendshipController {

    private final FriendshipService friendshipService;

    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    // sample url: /friendships/send?fromUserId=1&toUserId=2
    @PostMapping("/send")
    public ResponseEntity<Friendship> sendFriendRequest(@RequestParam Long fromUserId, @RequestParam Long toUserId) {
        Friendship friendship = friendshipService.sendFriendRequest(fromUserId, toUserId);
        return ResponseEntity.ok(friendship);
    }

    // sample url: /friendships/accept?friendshipId=1
    @PutMapping("/accept")
    public ResponseEntity<Friendship> acceptFriendRequest(@RequestParam Long friendshipId) {
        Friendship friendship = friendshipService.acceptFriendRequest(friendshipId);
        return ResponseEntity.ok(friendship);
    }

    @PutMapping("/reject")
    public ResponseEntity<Friendship> rejectFriendRequest(@RequestParam Long friendshipId) {
        Friendship friendship = friendshipService.rejectFriendRequest(friendshipId);
        return ResponseEntity.ok(friendship);
    }

    @GetMapping("/accepted/{userId}")
    public ResponseEntity<List<User>> getAcceptedFriends(@PathVariable Long userId) {
        List<User> friends = friendshipService.getAcceptedFriends(userId);
        return ResponseEntity.ok(friends);
    }

    @GetMapping("/pending/{userId}")
    public ResponseEntity<List<Friendship>> getPendingFriendRequests(@PathVariable Long userId) {
        List<Friendship> pendingRequests = friendshipService.getPendingFriendRequests(userId);
        return ResponseEntity.ok(pendingRequests);
    }

    @DeleteMapping("/remove/{friendshipId}")
    public ResponseEntity<Void> removeFriendship(@PathVariable Long friendshipId) {
        friendshipService.removeFriend(friendshipId);
        return ResponseEntity.noContent().build();
    }

    // sample url: /friendships/block?fromUserId=1&toUserId=2
    @PutMapping("/block")
    public ResponseEntity<String> blockUser(@RequestParam Long fromUserId, @RequestParam Long toUserId) {
        friendshipService.blockUser(fromUserId, toUserId);
        return ResponseEntity.ok(fromUserId + " Blocked " + toUserId);
    }
}