package com.team9oogling.codyus.domain.post.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponseDTO {

    private Long id;
    private Long userId;
    private Long categoryId;
    private String title;
    private String content;
    private String status;
    private Double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> hashtags;
}
