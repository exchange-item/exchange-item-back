package com.bakooza.bakooza.Board;

import com.bakooza.bakooza.Entity.Board;
import com.bakooza.bakooza.Repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BoardTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    void save() {

        // 1. 게시글 파라미터 생성
        Board params = Board.builder()
                .title("testPost")
                .userAddress("명지로 116번길 23-1")
                .content("some contents")
                .writer("Mootata")
                .categoryId(1)
                .memberId(10L)
                .build();

        // 2. 게시글 저장
        boardRepository.save(params);

        // 3. 1번 게시글 정보 조회
        Board entity = boardRepository.findById(1L).get();
        assertThat(entity.getTitle()).isEqualTo("testPost");
        assertThat(entity.getUserAddress()).isEqualTo("명지로 116번길 23-1");
        assertThat(entity.getContent()).isEqualTo("some contents");
        assertThat(entity.getWriter()).isEqualTo("Mootata");
        assertThat(entity.getCategoryId()).isEqualTo(1);
        assertThat(entity.getMemberId()).isEqualTo(10L);
    }

    @Test
    void findAll() {
        // 1. 전체 게시글 리스트 조회
        List<Board> boards = boardRepository.findAll();
    }

    @Test
    void delete() {

        // 1. 게시글 조회
        Board entity = boardRepository.findById(1L).get();

        // 2. 게시글 삭제
        boardRepository.delete(entity);
    }

}