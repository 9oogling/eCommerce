package com.team9oogling.codyus.global.config;

import com.team9oogling.codyus.domain.post.repository.CategoryRepository;
import com.team9oogling.codyus.domain.post.service.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryInitializer {

  private final CategoryRepository categoryRepository;
  private final PostService postService;

  public CategoryInitializer(CategoryRepository categoryRepository, PostService postService) {
    this.categoryRepository = categoryRepository;
    this.postService = postService;
  }

  @Bean
  public CommandLineRunner initCategory() {
    return args -> {

      // 메뉴 카테고리
      postService.addCategoryIfNotExists(categoryRepository, "MAN");
      postService.addCategoryIfNotExists(categoryRepository, "WOMAN");
      postService.addCategoryIfNotExists(categoryRepository, "UNISEX");
      postService.addCategoryIfNotExists(categoryRepository, "MAN,SPRING");




      postService.addCategoryIfNotExists(categoryRepository, "SEASON");
      postService.addCategoryIfNotExists(categoryRepository, "SPRING");
      postService.addCategoryIfNotExists(categoryRepository, "SUMMER");
      postService.addCategoryIfNotExists(categoryRepository, "FALL");
      postService.addCategoryIfNotExists(categoryRepository, "WINTER");


      // 분류 카테고리
      postService.addCategoryIfNotExists(categoryRepository, "TOPS");
      postService.addCategoryIfNotExists(categoryRepository, "BOTTOMS");
      postService.addCategoryIfNotExists(categoryRepository, "DRESS");
      postService.addCategoryIfNotExists(categoryRepository, "SUITS");
      postService.addCategoryIfNotExists(categoryRepository, "UNDERWEAR");
      postService.addCategoryIfNotExists(categoryRepository, "HAT");
      postService.addCategoryIfNotExists(categoryRepository, "GLASSES");
      postService.addCategoryIfNotExists(categoryRepository, "SUNGLASSES");
      postService.addCategoryIfNotExists(categoryRepository, "TIE");
    };
  }

}
