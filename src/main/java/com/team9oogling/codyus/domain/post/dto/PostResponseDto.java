package com.team9oogling.codyus.domain.post.dto;

import com.team9oogling.codyus.domain.post.entity.Post;
import com.team9oogling.codyus.domain.post.entity.SaleType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private int price;
    private SaleType saleType;
    private List<String> hashtags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.price = post.getPrice();
        this.saleType = post.getSaleType();
        this.hashtags = post.getHashtags();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getModifiedAt();
    }
}
