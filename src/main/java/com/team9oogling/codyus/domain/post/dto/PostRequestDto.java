package com.team9oogling.codyus.domain.post.dto;


import com.team9oogling.codyus.domain.post.entity.SaleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

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

    private String hashtags;

    @NotBlank
    private String categoryName;

    //여기에 이미지 업로드 추가
}
