package com.team9oogling.codyus.domain.post.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team9oogling.codyus.domain.post.entity.Post;
import com.team9oogling.codyus.domain.post.entity.QPost;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Post> findByTitle(String title) {
        QPost post = QPost.post;
        return queryFactory.selectFrom(post)
                .where(post.title.containsIgnoreCase(title))
                .fetch();
    }


    @Override
    public List<Post> findByHashTag(String hashtag) {
        QPost post = QPost.post;
        return queryFactory.selectFrom(post)
                .where(post.hashtags.any().eq(hashtag))
                .fetch();
    }

    @Override
    public List<Post> findByTitleOrContent(String keyword) {
        QPost post = QPost.post;
        return queryFactory.selectFrom(post)
                .where(post.title.containsIgnoreCase(keyword)
                        .or(post.content.containsIgnoreCase(keyword)))
                .fetch();
    }


}
