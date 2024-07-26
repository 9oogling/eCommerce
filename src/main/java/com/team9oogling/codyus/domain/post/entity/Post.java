package com.team9oogling.codyus.domain.post.entity;

import com.team9oogling.codyus.domain.user.entity.User;
import com.team9oogling.codyus.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostCategoryMatches> postCategoryMatches = new ArrayList<>();

    @Column
    private String category;

    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private PostStatus status = PostStatus.INPROGRESS;

    @Enumerated(EnumType.STRING)
    private SaleType saleType;

    private int price;

    private String hashtags;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



    public Post(String title, String content, int price, SaleType saleType, String hashtags, User user, Category category) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.saleType = saleType;
        this.hashtags = hashtags;
        this.user = user;
        this.category = category.getCategory();

        PostCategoryMatches postCategoryMatches = new PostCategoryMatches(this, category);
        this.postCategoryMatches.add(postCategoryMatches);
        category.getPostCategoryMatches().add(postCategoryMatches);
    }


    public void update(String title, String content, int price, SaleType saleType, String hashtags, User user, String category) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.saleType = saleType;
        this.hashtags = hashtags;
        this.user = user;
        this.category = category;
    }

}
