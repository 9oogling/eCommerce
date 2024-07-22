package com.team9oogling.codyus.domain.post.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team9oogling.codyus.domain.post.entity.Post;
import com.team9oogling.codyus.domain.post.entity.QPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    @Override
    public List<Post> findPostsByTitleOrContent(String keyword) {
        QPost post = QPost.post;
        return queryFactory.selectFrom(post)
                .where(post.title.containsIgnoreCase(keyword)
                        .or(post.content.containsIgnoreCase(keyword)))
                .fetch();
    }

    @Override
    public List<Post> findPostsByHashtag(String hashtag) {
        QPost post = QPost.post;
        return queryFactory.selectFrom(post)
                .where(post.hashtags.any().containsIgnoreCase(hashtag)) // 수정된 부분
                .fetch();
    }
}
