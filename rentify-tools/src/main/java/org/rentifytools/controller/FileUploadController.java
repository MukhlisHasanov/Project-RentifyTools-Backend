package org.rentifytools.controller;

import org.rentifytools.service.CloudStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    @Autowired
    private CloudStorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        try {
            List<String> fileUrls = files.stream()
                    .map(file -> {
                        try {
                            return storageService.uploadFile(file);
                        } catch (IOException e) {
                            throw new RuntimeException("File upload failed: " + e.getMessage());
                        }
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(fileUrls);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null); // Возвращаем null в случае ошибки
        }
    }
}
