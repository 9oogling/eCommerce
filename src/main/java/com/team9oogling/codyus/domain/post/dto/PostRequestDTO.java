package com.team9oogling.codyus.domain.post.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostRequestDTO {

    private Long userId;
    private Long categoryId;
    private String title;
    private String content;
    private String status;
    private Double price;
    private List<String> hashtags;
}
