package com.socialgraph.analyzer.repository;

import com.socialgraph.analyzer.entity.Comment;
import com.socialgraph.analyzer.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    public List<Comment> findByPost(Post post);
}
