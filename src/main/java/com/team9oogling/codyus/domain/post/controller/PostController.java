package com.team9oogling.codyus.domain.post.controller;


import com.team9oogling.codyus.domain.post.dto.PostRequestDTO;
import com.team9oogling.codyus.domain.post.dto.PostResponseDTO;
import com.team9oogling.codyus.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {


    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody PostRequestDTO postRequestDTO) {
        PostResponseDTO postResponse = postService.createPost(postRequestDTO);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable Long id, @RequestBody PostRequestDTO postRequestDTO) {
        PostResponseDTO postResponse = postService.updatePost(id, postRequestDTO);
        return ResponseEntity.ok(postResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long id) {
        PostResponseDTO postResponse = postService.getPostById(id);
        return ResponseEntity.ok(postResponse);
    }


    @GetMapping("/search")
    public ResponseEntity<List<PostResponseDTO>> searchPosts(@RequestParam String keyword) {
        List<PostResponseDTO> posts = postService.searchPosts(keyword);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/searchByHashtag")
    public ResponseEntity<List<PostResponseDTO>> searchPostsByHashtag(@RequestParam String hashtag) {
        List<PostResponseDTO> posts = postService.searchPostsByHashtag(hashtag);
        return ResponseEntity.ok(posts);
    }




}
