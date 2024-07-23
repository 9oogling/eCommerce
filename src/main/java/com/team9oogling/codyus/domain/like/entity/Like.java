package com.team9oogling.codyus.domain.like.entity;

import com.team9oogling.codyus.domain.post.entity.Post;
import com.team9oogling.codyus.domain.user.entity.User;
import com.team9oogling.codyus.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "likes")
@NoArgsConstructor
public class Like extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}
