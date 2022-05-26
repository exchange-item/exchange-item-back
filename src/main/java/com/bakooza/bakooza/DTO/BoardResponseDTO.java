package com.bakooza.bakooza.DTO;


import com.bakooza.bakooza.Entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDTO {

    private final Long postId; // PK
    private final String title; // 제목
    private final String userAddress; //작성자 주소
    private final String content; // 내용
    private final String writer; // 작성자
    private final int hits; // 조회 수
    private final LocalDateTime createdDate; // 생성일
    private final LocalDateTime modifiedDate; // 수정일

    public BoardResponseDTO(Board entity) {
        this.postId = entity.getPostId();
        this.title = entity.getTitle();
        this.userAddress = entity.getUserAddress();
        this.content = entity.getContent();
        this.writer = entity.getWriter();
        this.hits = entity.getViews();
        this.createdDate = entity.getPostDate();
        this.modifiedDate = entity.getModifiedDate();
    }

}