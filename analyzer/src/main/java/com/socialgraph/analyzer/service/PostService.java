package com.socialgraph.analyzer.service;

import com.socialgraph.analyzer.entity.Post;
import com.socialgraph.analyzer.entity.User;
import com.socialgraph.analyzer.exception.PostNotFoundException;
import com.socialgraph.analyzer.exception.UserNotFoundException;
import com.socialgraph.analyzer.repository.PostRepository;
import com.socialgraph.analyzer.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final UserRepository userRepository;
    PostRepository postRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository)
    {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
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
    public void updatePostById(Long id, Post post)
    {
        Post postFound = postRepository.findById(id).orElseThrow
            (
                () -> new PostNotFoundException("This Post with Id: " + id + " not Found",
                HttpStatus.BAD_GATEWAY)
            );
        postFound.setPostContent(post.getPostContent());
        postFound.setPostImageUrl(post.getPostImageUrl());
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


}
