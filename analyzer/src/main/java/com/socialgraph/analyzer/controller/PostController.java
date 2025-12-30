package com.socialgraph.analyzer.controller;

import com.socialgraph.analyzer.dto.PostRequestDto;
import com.socialgraph.analyzer.entity.Post;
import com.socialgraph.analyzer.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostRequestDto postDto)
    {
        Post post = new Post();
        post.setPostContent(postDto.getContent());
        post.setPostImageUrl(postDto.getImageUrl());
        post.setPrivacy(postDto.getPrivacy());
        postService.createPost(post, postDto.getUserId());
        return ResponseEntity.ok("Post Is created successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id)
    {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<Iterable<Post>> getPostsByUserId(@PathVariable Long userId)
    {
        return ResponseEntity.ok(postService.getPostsByUser(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePostById(@PathVariable Long id, @RequestBody Post post)
    {
        postService.updatePostById(id, post);
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable Long id)
    {
        postService.removePostById(id);
        return ResponseEntity.ok("Post with id: " + id + " is removed Successfully.");
    }
}