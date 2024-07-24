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

    /*@ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;*/

    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @Enumerated(EnumType.STRING)
    private SaleType saleType;

    private Double price;

    @ElementCollection
    @CollectionTable(name = "post_hashtags", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "hashtag")
    private List<String> hashtags = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



    public Post(String title, String content, Double price, PostStatus status, SaleType saleType, List<String> hashtags, User user) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.status = status;
        this.saleType = saleType;
        this.hashtags = hashtags;
        this.user = user;
    }


    public void update(String title, String content, Double price, PostStatus status, SaleType saleType, List<String> hashtags, User user) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.status = status;
        this.saleType = saleType;
        this.hashtags = hashtags;
        this.user = user;
    }

}
