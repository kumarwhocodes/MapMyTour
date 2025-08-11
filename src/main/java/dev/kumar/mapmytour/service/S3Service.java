package dev.kumar.mapmytour.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final S3Client s3Client;
    
    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public String uploadImage(MultipartFile file) {
        try {
            // Generate unique filename
            String fileName = "tours/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
            
            // Validate file type
            if (!isValidImageType(file.getContentType())) {
                throw new IllegalArgumentException("Invalid file type. Only JPEG, PNG, and GIF are allowed.");
            }
            
            // Upload to S3
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            
            // Return public URL
            String imageUrl = s3Client.utilities().getUrl(GetUrlRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build()).toExternalForm();
                    
            log.info("Image uploaded successfully: {}", imageUrl);
            return imageUrl;
            
        } catch (IOException e) {
            log.error("Error uploading image to S3", e);
            throw new RuntimeException("Failed to upload image", e);
        }
    }
    
    private boolean isValidImageType(String contentType) {
        return contentType != null && (
            contentType.equals("image/jpeg") ||
            contentType.equals("image/png") ||
            contentType.equals("image/gif")
        );
    }
}
