package org.rentifytools.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class CloudStorageService {
    private final Storage storage = StorageOptions.getDefaultInstance().getService();
    private final String bucketName = "rentify_tool";

    public String uploadImage(MultipartFile file) {
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        try {
            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName).build();
            storage.create(blobInfo, file.getBytes());
            return "https://storage.googleapis.com/" + bucketName + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }
}

