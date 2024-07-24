package com.team9oogling.codyus.domain.post.service;


import com.team9oogling.codyus.domain.post.dto.PostRequestDto;
import com.team9oogling.codyus.domain.post.dto.PostResponseDto;
import com.team9oogling.codyus.domain.post.entity.Post;
import com.team9oogling.codyus.domain.post.entity.SearchType;
import com.team9oogling.codyus.domain.post.repository.PostRepository;
import com.team9oogling.codyus.domain.post.repository.PostRepositoryImpl;
import com.team9oogling.codyus.domain.user.entity.User;
import com.team9oogling.codyus.domain.user.repository.UserRepository;
import com.team9oogling.codyus.global.entity.StatusCode;
import com.team9oogling.codyus.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostRepositoryImpl postRepositoryImpl;
    // private CategoryRepository categoryRepository;


    public PostResponseDto savePost(PostRequestDto requestDto, String email) {
        User user = userRepository.findByemail(email)
                .orElseThrow(()-> new CustomException(StatusCode.NOT_FOUND_USER));


        Post post = new Post(requestDto.getTitle(), requestDto.getContent(), requestDto.getPrice(),
                requestDto.getStatus(), requestDto.getSaleType(), requestDto.getHashtags(), user);
        post = postRepository.save(post);

        return new PostResponseDto(post);
    }


    public PostResponseDto updatePost(Long postId, PostRequestDto requestDto, User user) {
        Post post = findById(postId);
        checkUserSame(post, user);
        post.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getPrice(),
                requestDto.getStatus(), requestDto.getSaleType(), requestDto.getHashtags(), user);

        return new PostResponseDto(post);
    }

    public String deletePost(Long postId, User user) {
        Post post = findById(postId);
        checkUserSame(post, user);
        postRepository.delete(post);

        return postId + "번 게시물이 삭제 되었습니다.";
    }


    public List<PostResponseDto> findAllPost(int page, int size) {
        Page<Post> postsPage = postRepository.findAll(PageRequest.of(page, size));
        return postsPage.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    public long countAllPosts() {
        return postRepository.count();
    }

    public PostResponseDto getPost(Long postId) {
        Post post = findById(postId);
        
        return new PostResponseDto(post);
    }
    

    public Post findById(Long postId){
        return postRepository.findById(postId).orElseThrow(
                ()-> new CustomException(StatusCode.NOT_FOUND_POST));
    }

    private void checkUserSame(Post post, User user) {
        if(!post.getUser().getId().equals(user.getId())){
            throw new CustomException(StatusCode.UNAUTHORIZED);
        }
    }


    public List<PostResponseDto> searchPosts(SearchType type, String value) {
        List<Post> posts;

        switch (type) {
            case TITLE:
                posts = postRepository.findByTitle(value);
                break;
            case CONTENT:
                posts = postRepository.findByTitleOrContent(value);
                break;
            case HASHTAG:
                posts = postRepository.findByHashTag(value);
                break;
            default:
                throw new CustomException(StatusCode.NOT_FOUND_POST);
        }

        // Post 엔티티를 PostResponseDto로 변환
        return posts.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<PostResponseDto> findMyPosts(String email) {
        User user = userRepository.findByemail(email)
                .orElseThrow(()-> new CustomException(StatusCode.NOT_FOUND_USER));

        List<Post> posts = postRepository.findByUser(user);
        return posts.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }
}
