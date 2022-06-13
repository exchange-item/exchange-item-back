package com.bakooza.bakooza.DTO;

import com.bakooza.bakooza.Entity.PostImage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageRequestDTO {
    private String imagePath; // 이미지 경로
    private Long postId; // 게시글 번호
    private String imageSize; // 이미지 사이즈
    private LocalDate uploadDate; // 업로드 날짜

    public PostImage toEntity() {
        return PostImage.builder()
                .imagePath(imagePath)
                .postId(postId)
                .imageSize(imageSize)
                .uploadDate(uploadDate)
                .build();
    }
}
