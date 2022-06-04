package com.bakooza.bakooza.Repository;

import com.bakooza.bakooza.DTO.ImageResponseDTO;
import com.bakooza.bakooza.Entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, String> {
    List<ImageResponseDTO> findByPostId(Long postId);
}
