package com.team9oogling.codyus.domain.post.service;


import com.team9oogling.codyus.domain.post.dto.PostRequestDTO;
import com.team9oogling.codyus.domain.post.dto.PostResponseDTO;
import com.team9oogling.codyus.domain.post.entity.Post;
import com.team9oogling.codyus.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class PostService {

    private final PostRepository postRepository;

    public PostResponseDTO createPost(PostRequestDTO postRequestDTO) {
        Post post = new Post();
        post.setUserId(postRequestDTO.getUserId());
        post.setCategoryId(postRequestDTO.getCategoryId());
        post.setTitle(postRequestDTO.getTitle());
        post.setContent(postRequestDTO.getContent());
        post.setStatus(postRequestDTO.getStatus());
        post.setPrice(postRequestDTO.getPrice());
        post.setHashtags(postRequestDTO.getHashtags());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        Post savedPost = postRepository.save(post);

        return convertToDTO(savedPost);
    }

    public PostResponseDTO updatePost(Long id, PostRequestDTO postRequestDTO) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setUserId(postRequestDTO.getUserId());
        post.setCategoryId(postRequestDTO.getCategoryId());
        post.setTitle(postRequestDTO.getTitle());
        post.setContent(postRequestDTO.getContent());
        post.setStatus(postRequestDTO.getStatus());
        post.setPrice(postRequestDTO.getPrice());
        post.setHashtags(postRequestDTO.getHashtags());
        post.setUpdatedAt(LocalDateTime.now());

        Post updatedPost = postRepository.save(post);

        return convertToDTO(updatedPost);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public PostResponseDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        return convertToDTO(post);
    }



    public List<PostResponseDTO> searchPosts(String keyword) {
        List<Post> posts = postRepository.findPostsByTitleOrContent(keyword);
        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<PostResponseDTO> searchPostsByHashtag(String hashtag) {
        List<Post> posts = postRepository.findPostsByHashtag(hashtag);
        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private PostResponseDTO convertToDTO(Post post) {
        PostResponseDTO dto = new PostResponseDTO();
        dto.setId(post.getId());
        dto.setUserId(post.getUserId());
        dto.setCategoryId(post.getCategoryId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setStatus(post.getStatus());
        dto.setPrice(post.getPrice());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setHashtags(post.getHashtags());
        return dto;
    }




}
