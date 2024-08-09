package com.team9oogling.codyus.domain.like.controller;

import com.team9oogling.codyus.domain.like.service.LikeService;
import com.team9oogling.codyus.global.dto.DataResponseDto;
import com.team9oogling.codyus.global.security.UserDetailsImpl;
import com.team9oogling.codyus.global.dto.MessageResponseDto;
import com.team9oogling.codyus.global.entity.ResponseFactory;
import com.team9oogling.codyus.global.entity.StatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{postId}/likes/count")
    public ResponseEntity<DataResponseDto<Integer>> getLikeCount(@PathVariable Long postId) {
        int likeCount = likeService.likecount(postId);
        return ResponseFactory.ok(likeCount, StatusCode.SUCCESS_GET_LIKECOUNT);

    }

    @GetMapping("/{postId}/likes/status")
    public ResponseEntity<DataResponseDto<Boolean>> getLikeStatus(@PathVariable Long postId,
                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean isLiked = likeService.isLiked(postId, userDetails);
        return ResponseFactory.ok(isLiked, StatusCode.SUCCESS_GET_LIKESTATUS);
    }

}
