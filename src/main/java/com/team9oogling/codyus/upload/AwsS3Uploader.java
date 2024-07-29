package com.team9oogling.codyus.upload;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.team9oogling.codyus.domain.post.entity.Post;
import com.team9oogling.codyus.domain.post.entity.PostImage;
import com.team9oogling.codyus.domain.post.repository.PostImageRepository;
import com.team9oogling.codyus.domain.post.repository.PostRepository;
import com.team9oogling.codyus.global.entity.StatusCode;
import com.team9oogling.codyus.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AwsS3Uploader {

    private final AmazonS3Client amazonS3Client;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private String random = UUID.randomUUID().toString().substring(0, 10);

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public void uploadImage(List<MultipartFile> files, ImageType type, Long typeId) {

        // NullCheck
        if (files.size() == 0) {
            return;
        }

        ArrayList<String> imageUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename(); // 원본 파일 명
            String s3FileName = random + originalFilename; // 변경된 파일 명 (같은 이름의 파일 방지)
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            try {
                amazonS3Client.putObject(bucket, s3FileName, file.getInputStream(), metadata); // 파일 업로드

                imageUrls.add(amazonS3Client.getUrl(bucket, s3FileName).toString());
            } catch (IOException e) {
                throw new CustomException(StatusCode.FILE_CONVERT_FAIL);
            }
        }

        switch (type) {
            case POST -> {
                Post post = postRepository.findById(typeId).orElseThrow(() ->
                        new CustomException(StatusCode.NOT_FOUND_POST));

                for (String imageUrl : imageUrls) {
                    PostImage postImage = new PostImage(post, imageUrl);
                    postImageRepository.save(postImage);
                }
            }
            case LIKES -> {

            }

            case MESSAGE -> {

            }

        }


    }


}
