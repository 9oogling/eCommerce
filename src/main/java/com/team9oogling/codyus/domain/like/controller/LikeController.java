package com.team9oogling.codyus.domain.like.controller;

import com.team9oogling.codyus.domain.like.dto.LikedPostResponseDto;
import com.team9oogling.codyus.domain.like.service.LikeService;
import com.team9oogling.codyus.global.dto.DataResponseDto;
import com.team9oogling.codyus.global.dto.MessageResponseDto;
import com.team9oogling.codyus.global.entity.ResponseFactory;
import com.team9oogling.codyus.global.entity.StatusCode;
import com.team9oogling.codyus.global.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{postId}/likes")
    public ResponseEntity<MessageResponseDto> addLike(@PathVariable Long postId,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.addLike(postId, userDetails);

        return ResponseFactory.created(StatusCode.SUCCESS_ADD_LIKE);
    }

    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<MessageResponseDto> unLike(@PathVariable Long postId,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.unLike(postId, userDetails);

        return ResponseFactory.ok(StatusCode.SUCCESS_DELETE_LIKE);
    }

    // 사용자가 좋아요 한 목록 조회
    @GetMapping("/likes/my")
    public ResponseEntity<DataResponseDto<List<LikedPostResponseDto>>> getLikedPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<LikedPostResponseDto> likedPosts = likeService.getLikedPosts(userDetails);

        return ResponseFactory.ok(likedPosts, StatusCode.SUCCESS_GET_LIKE);
    }
}
