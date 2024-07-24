package com.team9oogling.codyus.domain.post.repository;

import com.team9oogling.codyus.domain.post.entity.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  Optional<Category> findByCategory(String category);

}
