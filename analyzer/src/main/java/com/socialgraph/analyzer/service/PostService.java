package com.socialgraph.analyzer.service;

import com.socialgraph.analyzer.dto.PostRequestDto;
import com.socialgraph.analyzer.entity.Post;
import com.socialgraph.analyzer.entity.Privacy;
import com.socialgraph.analyzer.entity.User;
import com.socialgraph.analyzer.exception.PostNotFoundException;
import com.socialgraph.analyzer.exception.UserNotFoundException;
import com.socialgraph.analyzer.repository.PostRepository;
import com.socialgraph.analyzer.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostService {

    private final UserRepository userRepository;
    PostRepository postRepository;
    FriendshipService friendshipService;

    public PostService(PostRepository postRepository, UserRepository userRepository, FriendshipService friendshipService)
    {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.friendshipService = friendshipService;
    }

    @Transactional
    public void createPost(Post post, Long userId)
    {
        User user = userRepository.findById(userId).orElseThrow
                (() -> new UserNotFoundException("User for id: " + userId + " Not found",
                        HttpStatus.BAD_GATEWAY));
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);
    }

    public Post getPostById(Long id)
    {
        return postRepository.findById(id).orElseThrow
            (
                () -> new PostNotFoundException("This Post with Id: " + id + " not Found",
                HttpStatus.BAD_GATEWAY)
            );
    }

    public List<Post> getAllPosts()
    {
        return postRepository.findAll();
    }

    @Transactional
    public void updatePostById(Long id, PostRequestDto post)
    {
        Post postFound = postRepository.findById(id).orElseThrow
            (
                () -> new PostNotFoundException("This Post with Id: " + id + " not Found",
                HttpStatus.BAD_GATEWAY)
            );
        postFound.setPostContent(post.getContent());
        postFound.setPostImageUrl(post.getImageUrl());
        postFound.setPrivacy(post.getPrivacy());
        postFound.setUpdatedAt(LocalDateTime.now());
        postRepository.save(postFound);
    }

    @Transactional
    public void removePostById(Long id)
    {
        postRepository.deleteById(id);
    }

    public List<Post> getPostsByUser(Long userId)
    {
        User user = userRepository.findById(userId).orElseThrow
                (() -> new UserNotFoundException("User for id: " + userId + " Not found",
                        HttpStatus.BAD_GATEWAY));

        return postRepository.findByUser(user);
    }

    public List<Post> getPostsByViewer(Long ownerId, Long viewerId)
    {
        User owner = userRepository.findById(ownerId).orElseThrow
                (() -> new UserNotFoundException("Owner for id: " + ownerId + " Not found",
                        HttpStatus.BAD_GATEWAY));

        userRepository.findById(viewerId).orElseThrow
                (() -> new UserNotFoundException("Viewer for id: " + viewerId + " Not found",
                        HttpStatus.BAD_GATEWAY));

        List<Post> totalPosts = postRepository.findByUser(owner);
        List<Post> publicPosts = totalPosts.stream().
                                 filter(post -> post.getPrivacy().equals(Privacy.PUBLIC)).
                                 toList();
        List<Post> privatePosts = totalPosts.stream().filter(post -> (post.getPrivacy().
                equals(Privacy.PRIVATE))).filter(post -> post.getUser().getId().equals(viewerId)).toList();

        Set<Long> ownerFriends = friendshipService
                .getAcceptedFriends(ownerId)
                .stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        List<Post> friendsOnlyPosts = totalPosts.stream()
                .filter(post ->
                        post.getPrivacy() == Privacy.FRIENDS_ONLY &&
                                ownerFriends.contains(viewerId)
                )
                .toList();

        List<Post> merged = new ArrayList<>();
        merged.addAll(publicPosts);
        merged.addAll(privatePosts);
        merged.addAll(friendsOnlyPosts);
        return merged;
    }


}
