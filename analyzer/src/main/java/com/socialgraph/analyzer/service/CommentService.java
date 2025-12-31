package com.socialgraph.analyzer.service;

import com.socialgraph.analyzer.entity.Comment;
import com.socialgraph.analyzer.entity.Post;
import com.socialgraph.analyzer.entity.User;
import com.socialgraph.analyzer.exception.PostNotFoundException;
import com.socialgraph.analyzer.exception.UserNotFoundException;
import com.socialgraph.analyzer.repository.CommentRepository;
import com.socialgraph.analyzer.repository.PostRepository;
import com.socialgraph.analyzer.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    // Injecting Dependencies
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public Comment addComment(Long postId, Long userId, String content) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        // Fetch User and Post entities
        User user = userRepository.findById(userId).orElseThrow
                (() -> new UserNotFoundException("User for id: " + userId + " Not found",
                        HttpStatus.NOT_FOUND));
        Post post = postRepository.findById(postId).orElseThrow
                (() -> new PostNotFoundException("Post for id: " + postId + " Not found",
                        HttpStatus.NOT_FOUND));

        comment.setUser(user);
        comment.setPost(post);

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPost(Long postId) {

        // Fetch Post entity if exists else throw exception
        Post post = postRepository.findById(postId).orElseThrow
                (() -> new PostNotFoundException("Post for id: " + postId + " Not found",
                        HttpStatus.NOT_FOUND));

        return commentRepository.findByPost(post);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
