package com.socialgraph.analyzer.repository;

import com.socialgraph.analyzer.entity.Post;
import com.socialgraph.analyzer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    public List<Post> findByUser(User user);
}
