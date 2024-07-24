package com.team9oogling.codyus.domain.post.repository;

import com.team9oogling.codyus.domain.post.entity.Post;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepositoryCustom {
    //제목으로 검색
    List<Post> findByTitle(String title);

    //해시태그로 검색
    List<Post> findByHashTag(@Param("hashtag") String hashtag);

    List<Post> findByTitleOrContent(@Param("keyword") String keyword);
}
