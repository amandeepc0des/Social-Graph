package com.socialgraph.analyzer.controller;

import com.socialgraph.analyzer.dto.LikeRequestDto;
import com.socialgraph.analyzer.entity.Like;
import com.socialgraph.analyzer.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public ResponseEntity<Like> addLike(@RequestBody LikeRequestDto likeRequestDto) {
        Like like = likeService.addLike(likeRequestDto);
        return ResponseEntity.ok(like);
    }

    @DeleteMapping("/{likeId}")
    public ResponseEntity<String> removeLike(@PathVariable Long likeId) {
        likeService.removeLike(likeId);
        return ResponseEntity.ok("Like removed successfully");
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Like>> getLikesByPostId(@PathVariable Long postId) {
        List<Like> likes = likeService.getLikesByPostId(postId);
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Like>> getLikesByUserId(@PathVariable Long userId) {
        List<Like> likes = likeService.getLikesByUserId(userId);
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/post/{postId}/count")
    public ResponseEntity<String> countLikesByPostId(@PathVariable Long postId) {
        long count = likeService.countLikesByPostId(postId);
        return ResponseEntity.ok("LikesCount for PostId " + postId + " is: " + count);
    }
}