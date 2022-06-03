package com.bakooza.bakooza.Service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AwsS3Service {
    public List<String> uploadFile(List<MultipartFile> multipartFile, Long postId);

    public void deleteFile(String fileName);

    public List<String> getFile(List<String> fileNameList);
}
