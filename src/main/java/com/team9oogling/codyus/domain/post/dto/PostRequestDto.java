package com.team9oogling.codyus.domain.post.dto;


import com.team9oogling.codyus.domain.post.entity.Category;
import com.team9oogling.codyus.domain.post.entity.PostStatus;
import com.team9oogling.codyus.domain.post.entity.SaleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostRequestDto {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private int price;
    @NotNull
    private SaleType saleType;

    private List<String> hashtags;

    @NotBlank
    private String categoryName;

    //여기에 이미지 업로드 추가
}
