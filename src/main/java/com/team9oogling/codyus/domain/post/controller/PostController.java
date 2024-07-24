package com.team9oogling.codyus.domain.post.controller;


import com.team9oogling.codyus.domain.post.dto.PostRequestDto;
import com.team9oogling.codyus.domain.post.dto.PostResponseDto;
import com.team9oogling.codyus.domain.post.entity.SearchType;
import com.team9oogling.codyus.domain.post.service.PostService;
import com.team9oogling.codyus.domain.user.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {


    private final PostService postService;

    //게시물 생성
    @PostMapping
    public ResponseEntity savePost(@Valid @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String email = userDetails.getUsername(); //email을 username으로 사용
        PostResponseDto postResponseDto = postService.savePost(requestDto, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponseDto);
    }

    //수정
    @PutMapping("/{postId}")
    public ResponseEntity updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(postId, postRequestDTO, userDetails.getUser()));
    }

    //삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.deletePost(postId, userDetails.getUser()));
    }

    //게시물 전체조회
    @GetMapping
    public List<PostResponseDto> getAllPost(@RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "5") int size) {
        return postService.findAllPost(page, size);
    }

    // 게시물 선택 조회
    @GetMapping("/{postId}")
    public ResponseEntity getPost(@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(postId));
    }

    @GetMapping("/my")
    public List<PostResponseDto> getMyPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String email = userDetails.getUsername();
        return postService.findMyPosts(email);
    }

    // 게시물 검색
    @GetMapping("/search")
    public ResponseEntity<List<PostResponseDto>> searchPosts(
            @RequestParam SearchType type,
            @RequestParam String value) {
        List<PostResponseDto> posts = postService.searchPosts(type, value);
        return ResponseEntity.ok(posts);
    }



}
