package com.socialgraph.analyzer.service;

import com.socialgraph.analyzer.entity.Friendship;
import com.socialgraph.analyzer.entity.FriendshipStatus;
import com.socialgraph.analyzer.entity.User;
import com.socialgraph.analyzer.exception.CannotFriendYourselfException;
import com.socialgraph.analyzer.exception.DuplicateFriendRequestException;
import com.socialgraph.analyzer.exception.FriendshipNotFoundException;
import com.socialgraph.analyzer.exception.UserNotFoundException;
import com.socialgraph.analyzer.repository.FriendshipRepository;
import com.socialgraph.analyzer.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendshipService {

    FriendshipRepository friendshipRepository;
    UserRepository userRepository;

    public FriendshipService(FriendshipRepository friendshipRepository, UserRepository userRepository)
    {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    // send friend request
    public Friendship sendFriendRequest(Long fromUserId, Long toUserId)
    {
        User fromUser =  userRepository.findById(fromUserId).orElseThrow(() -> new UserNotFoundException("User with id " + fromUserId + " not found", HttpStatus.NOT_FOUND));
        User toUser = userRepository.findById(toUserId).orElseThrow(() -> new UserNotFoundException("User with id " + toUserId + " not found", HttpStatus.NOT_FOUND));

        // Check if a friendship already exists
        if (friendshipRepository.existsByFromUserIdAndToUserId(fromUser, toUser) ||
            friendshipRepository.existsByFromUserIdAndToUserId(toUser, fromUser)) {
            throw new DuplicateFriendRequestException();
        }

        // Check if the user is trying to friend themselves
        if (fromUserId.equals(toUserId)) {
            throw new CannotFriendYourselfException();
        }

        Friendship friendship = new Friendship();
        friendship.setFromUserId(fromUser);
        friendship.setToUserId(toUser);
        friendship.setFriendshipStatus(FriendshipStatus.PENDING);
        friendship.setCreatedAt(LocalDateTime.now());
        friendship.setUpdatedAt(LocalDateTime.now());
        return friendshipRepository.save(friendship);
    }

    // Accept friend request
    public Friendship acceptFriendRequest(Long friendshipId)
    {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new FriendshipNotFoundException("Friendship with id " + friendshipId + " not found"));
        friendship.setFriendshipStatus(FriendshipStatus.ACCEPTED);
        friendship.setUpdatedAt(LocalDateTime.now());
        return friendshipRepository.save(friendship);
    }

    // Reject friend request
    public Friendship rejectFriendRequest(Long friendshipId)
    {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new FriendshipNotFoundException("Friendship with id " + friendshipId + " not found"));
        friendship.setFriendshipStatus(FriendshipStatus.REJECTED);
        friendship.setUpdatedAt(LocalDateTime.now());
        return friendshipRepository.save(friendship);
    }

    // Get Accepted Friends for a User.
    public List<User> getAcceptedFriends(Long userId)
    {
        User user =  userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found", HttpStatus.NOT_FOUND));

        List<Friendship> friendships = friendshipRepository.findByFromUserIdAndFriendshipStatus(user, FriendshipStatus.ACCEPTED);
        List<Long> friendIds = friendships.stream()
                .map(friendship -> friendship.getFromUserId().getId())
                .collect(Collectors.toList());
        return userRepository.findAllById(friendIds);
    }

    // Get Pending Friend Requests for a User.
    public List<Friendship> getPendingFriendRequests(Long userId)
    {
        User user =  userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found", HttpStatus.NOT_FOUND));

        return friendshipRepository.findByToUserIdAndFriendshipStatus(user, FriendshipStatus.PENDING);
    }

    //Remove Friend
    public void removeFriend(Long FriendshipId)
    {
        Friendship friendship = friendshipRepository.findById(FriendshipId)
                .orElseThrow(() -> new FriendshipNotFoundException("Friendship with id " + FriendshipId + " not found"));
        friendshipRepository.delete(friendship);
    }

    //Block User
    public void blockUser(Long blockerId, Long blockedId)
    {
        User fromUser =  userRepository.findById(blockerId).orElseThrow(() -> new UserNotFoundException("User with id " + blockerId + " not found", HttpStatus.NOT_FOUND));
        User toUser = userRepository.findById(blockedId).orElseThrow(() -> new UserNotFoundException("User with id " + blockedId + " not found", HttpStatus.NOT_FOUND));

        Optional<Friendship> friendship = friendshipRepository.findByFromUserIdAndToUserId(fromUser, toUser);

        if (friendship.isPresent()) {
            friendship.get().setFriendshipStatus(FriendshipStatus.BLOCKED);
            friendshipRepository.save(friendship.get());
        } else {
            throw new FriendshipNotFoundException("Friendship between users not found");
        }
    }
}
