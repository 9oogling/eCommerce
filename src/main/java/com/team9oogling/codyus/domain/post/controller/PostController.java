package com.team9oogling.codyus.domain.post.controller;


import com.team9oogling.codyus.domain.post.dto.PostRequestDto;
import com.team9oogling.codyus.domain.post.dto.PostResponseDto;
import com.team9oogling.codyus.domain.post.entity.SearchType;
import com.team9oogling.codyus.domain.post.service.PostService;
import com.team9oogling.codyus.global.dto.DataResponseDto;
import com.team9oogling.codyus.global.dto.MessageResponseDto;
import com.team9oogling.codyus.global.entity.ResponseFactory;
import com.team9oogling.codyus.global.entity.StatusCode;
import com.team9oogling.codyus.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {


    private final PostService postService;

    //게시물 생성
    @PostMapping
    public ResponseEntity<DataResponseDto<PostResponseDto>> savePost(@Valid @RequestPart(value = "request") PostRequestDto requestDto,
                                                                     @RequestPart(value = "image", required = false) List<MultipartFile> images,
        @RequestPart(value = "productImage", required = false) List<MultipartFile> productImages,
                                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto responseDto = postService.savePost(requestDto, userDetails, images);

        return ResponseFactory.created(responseDto, StatusCode.SUCCESS_CREATE_POST);
    }

    //수정
    @PutMapping("/{postId}")
    public ResponseEntity<DataResponseDto<PostResponseDto>> updatePost(@PathVariable Long postId,
                                                                       @RequestBody PostRequestDto requestDto,
                                                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto responseDto = postService.updatePost(postId, requestDto, userDetails.getUser());

        return ResponseFactory.ok(responseDto, StatusCode.SUCCESS_UPDATE_POST);
    }

    //삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<MessageResponseDto> deletePost(@PathVariable Long postId,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails.getUser());

        return ResponseFactory.ok(StatusCode.SUCCESS_DELETE_POST);
    }

    //게시물 전체조회
    @GetMapping
    public ResponseEntity<DataResponseDto<List<PostResponseDto>>> getAllPost(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        List<PostResponseDto> posts = postService.findAllPost(page - 1, size);

        return ResponseFactory.ok(posts, StatusCode.SUCCESS_GET_ALLPOST);
    }

    // 게시물 선택 조회
    @GetMapping("/{postId}")
    public ResponseEntity<DataResponseDto<PostResponseDto>> getPost(@PathVariable Long postId) {

        PostResponseDto post = postService.getPost(postId);
        return ResponseFactory.ok(post, StatusCode.SUCCESS_GET_POST);
    }

    @GetMapping("/my")
    public ResponseEntity<DataResponseDto<List<PostResponseDto>>> getMyPosts(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<PostResponseDto> myPosts = postService.findMyPosts(userDetails);

        return ResponseFactory.ok(myPosts, StatusCode.SUCCESS_GET_MYPOST);
    }

    // 게시물 검색
    @GetMapping("/search")
    public ResponseEntity<DataResponseDto<Page<PostResponseDto>>> searchPosts(
            @RequestParam(required = false) SearchType type,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy, // Default sort by field
            @RequestParam(defaultValue = "false") boolean descending) { // Default sort direction) {

        Page<PostResponseDto> posts = postService.searchPosts(type, keyword, page - 1, size, sortBy, descending);
        return ResponseFactory.ok(posts, StatusCode.SUCCESS_SEARCH_POSTS);
    }


}
