package com.bakooza.bakooza.DTO;

import com.bakooza.bakooza.Entity.Board;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDTO {

    private String title; // 제목
    private String userLocation; // 작성자 주소
    private int categoryId; // 카테고리
    private String content; // 내용
    private String writer; // 작성자

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .userLocation(userLocation)
                .categoryId(categoryId)
                .content(content)
                .writer(writer)
                .build();
    }
}
