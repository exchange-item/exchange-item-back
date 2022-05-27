package com.bakooza.bakooza.Controller;

import com.bakooza.bakooza.DTO.BoardRequestDTO;
import com.bakooza.bakooza.DTO.BoardResponseDTO;
import com.bakooza.bakooza.Service.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
@ResponseBody
@RequiredArgsConstructor
public class BoardController {

    private final BoardServiceImpl boardService;

    // 게시글 작성
    @PostMapping("/boards")
    public Long save(@RequestBody final BoardRequestDTO params) {
        return boardService.save(params);
    }

    // 게시판 조회
    @GetMapping("/boards")
    public Page<BoardResponseDTO> findAll(@RequestParam final int categoryId,
                                          @PageableDefault(sort = "post_id", direction = Sort.Direction.DESC) final Pageable pageable) {
        return boardService.findByCategoryId(categoryId, pageable                                                                                                                                                ).map(BoardResponseDTO::new);
    }

    // 게시글 수정
    @PatchMapping("/boards/{id}")
    public Long update(@PathVariable final Long id, @RequestBody final BoardRequestDTO params) {
        return boardService.update(id, params);
    }

    // 게시글 삭제
    @DeleteMapping("/boards/{id}")
    public Long delete(@PathVariable final Long id) {
        return boardService.delete(id);
    }

    // 게시글 검색
    @GetMapping("/boards/search")
    public Page<BoardResponseDTO> search(@RequestParam final String keyword,
                                         @PageableDefault(sort = "post_id", direction = Sort.Direction.DESC) final Pageable pageable) {
        return boardService.search(keyword, pageable).map(BoardResponseDTO::new);
    }

    // 게시글 조회
    @GetMapping("/posts/{postId}")
    public BoardResponseDTO findById(@PathVariable final Long postId) {
        return boardService.findById(postId);
    }
}