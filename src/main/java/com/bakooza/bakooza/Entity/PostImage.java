package com.bakooza.bakooza.Entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post_image")
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private String imageId; // PK

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "image_size")
    private String imageSize;

    @Column(name = "upload_date")
    private LocalDate uploadDate;

    @Builder
    public PostImage(String imagePath, Long postId, String imageSize, LocalDate uploadDate) {
        this.postId = postId;
        this.imagePath = imagePath;
        this.imageSize = imageSize;
        this.uploadDate = uploadDate;
    }
}
