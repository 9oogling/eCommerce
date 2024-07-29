package com.team9oogling.codyus.domain.post.repository;

import com.team9oogling.codyus.domain.post.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
