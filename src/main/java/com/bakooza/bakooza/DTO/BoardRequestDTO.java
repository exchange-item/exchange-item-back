package com.bakooza.bakooza.DTO;

import com.bakooza.bakooza.Entity.Board;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDTO {

    private String title; // 제목
    private String userAddress; // 작성자 주소
    private String content; // 내용
    private String writer; // 작성자

    public Board toEntity() {
        return Board.builder()
            .title(title)
            .userAddress(userAddress)
            .content(content)
            .writer(writer)
            .build();
    }
}
