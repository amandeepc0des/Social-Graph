package com.socialgraph.analyzer.repository;

import com.socialgraph.analyzer.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findByPostId(Long postId);
    List<Like> findByUserId(Long userId);
    long countByPostId(Long postId);
    
}
