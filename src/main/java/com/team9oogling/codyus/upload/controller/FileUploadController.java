package com.team9oogling.codyus.upload.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.team9oogling.codyus.upload.service.AwsS3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class FileUploadController {

    private final AwsS3Uploader awsS3Uploader;

    @PostMapping("/posts/{postId}")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long postId) throws IOException {

        String fileName = awsS3Uploader.upload(file, "posts",postId);

        return ResponseEntity.ok(fileName);
    }
}