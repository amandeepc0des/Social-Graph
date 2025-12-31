package com.socialgraph.analyzer.repository;

import com.socialgraph.analyzer.entity.Friendship;
import com.socialgraph.analyzer.entity.FriendshipStatus;
import com.socialgraph.analyzer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    boolean existsByFromUserIdAndToUserId(User fromUserId, User toUserId);
    List<Friendship> findByFromUserIdAndFriendshipStatus(User fromUserId, FriendshipStatus status);
    List<Friendship> findByToUserIdAndFriendshipStatus(User toUserId, FriendshipStatus status);
    Optional<Friendship> findByFromUserIdAndToUserId(User fromUserId, User toUserId);
}
