package com.team9oogling.codyus.domain.post.repository;

import com.team9oogling.codyus.domain.post.entity.Post;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> findPostsByTitleOrContent(String keyword);
    List<Post> findPostsByHashtag(String hashtag);
}
