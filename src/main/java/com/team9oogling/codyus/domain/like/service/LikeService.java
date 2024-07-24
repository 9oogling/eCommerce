package com.team9oogling.codyus.domain.like.service;

import com.team9oogling.codyus.domain.like.entity.Like;
import com.team9oogling.codyus.domain.like.repository.LikeRepository;
import com.team9oogling.codyus.domain.post.entity.Post;
import com.team9oogling.codyus.domain.post.repository.PostRepository;
import com.team9oogling.codyus.domain.user.entity.User;
import com.team9oogling.codyus.domain.user.repository.UserRepository;
import com.team9oogling.codyus.domain.user.security.UserDetailsImpl;
import com.team9oogling.codyus.global.entity.StatusCode;
import com.team9oogling.codyus.global.exception.CustomException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public LikeService(LikeRepository likeRepository, PostRepository postRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void addLike(Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new CustomException(StatusCode.NOT_FOUND_POST));

        User user = userRepository.findByemail(userDetails.getUsername()).orElseThrow(()
                -> new CustomException(StatusCode.NOT_FOUND_USER));

        if (post.getUser().equals(user)) {
            throw new CustomException(StatusCode.CANNOT_LIKE_YOURS);
        }
        Optional<Like> checkLike = likeRepository.findByPostIdAndUserId(postId, user.getId());

        if (checkLike.isPresent()) {
            throw new CustomException(StatusCode.ALREADY_EXIST_LIKE);
        } else {
            Like like = new Like(user, post);

            likeRepository.save(like);
        }
    }

    @Transactional
    public void unLike(Long postId, UserDetailsImpl userDetails) {
        User user = userRepository.findByemail(userDetails.getUsername()).orElseThrow(()
                -> new CustomException(StatusCode.NOT_FOUND_USER));

        Like checkLike = likeRepository.findByPostIdAndUserId(postId, user.getId()).orElseThrow(()
                -> new CustomException(StatusCode.NOT_FOUND_LIKE));

        likeRepository.delete(checkLike);
    }
}
