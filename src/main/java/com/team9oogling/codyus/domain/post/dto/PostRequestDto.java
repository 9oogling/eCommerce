package com.team9oogling.codyus.domain.post.dto;


import com.team9oogling.codyus.domain.post.entity.Category;
import com.team9oogling.codyus.domain.post.entity.PostStatus;
import com.team9oogling.codyus.domain.post.entity.SaleType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostRequestDto {

    private String title;
    private String content;
    private PostStatus status;
    private Double price;

    private SaleType saleType;

    private List<String> hashtags;

    private String categoryName;

    //여기에 이미지 업로드 추가
}
