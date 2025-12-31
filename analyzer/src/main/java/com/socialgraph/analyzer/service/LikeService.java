package com.socialgraph.analyzer.service;

import com.socialgraph.analyzer.dto.LikeRequestDto;
import com.socialgraph.analyzer.entity.Like;
import com.socialgraph.analyzer.entity.Post;
import com.socialgraph.analyzer.entity.User;
import com.socialgraph.analyzer.exception.PostNotFoundException;
import com.socialgraph.analyzer.exception.UserNotFoundException;
import com.socialgraph.analyzer.repository.LikeRepository;
import com.socialgraph.analyzer.repository.PostRepository;
import com.socialgraph.analyzer.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public LikeService(LikeRepository likeRepository, UserRepository userRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public Like addLike(LikeRequestDto likeRequestDto) {
        User user = userRepository.findById(likeRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Post post = postRepository.findById(likeRequestDto.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        like.setLikedAt(LocalDateTime.now());

        return likeRepository.save(like);
    }

    @Transactional
    public void removeLike(Long likeId) {
        
        likeRepository.deleteById(likeId);
    }

    public List<Like> getLikesByPostId(Long postId) {
       postRepository.findById(postId).orElseThrow
                (() -> new PostNotFoundException("Post for id: " + postId + " Not found",
                        HttpStatus.NOT_FOUND));
        return likeRepository.findByPostId(postId);
    }

    public List<Like> getLikesByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow
                (() -> new UserNotFoundException("User for id: " + userId + " Not found",
                        HttpStatus.NOT_FOUND));
        return likeRepository.findByUserId(userId);
    }

    public long countLikesByPostId(Long postId) {
        postRepository.findById(postId).orElseThrow
                (() -> new PostNotFoundException("Post for id: " + postId + " Not found",
                        HttpStatus.NOT_FOUND));
        return likeRepository.countByPostId(postId);
    }

}
