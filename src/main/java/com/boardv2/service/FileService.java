package com.boardv2.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFile(MultipartFile file, String subDirectory);

    ResponseEntity<Resource> downloadFile(String subDirectory, String savedFilename, String originalFilename);

    void deleteFile(String subDirectory, String savedFilename);
}

