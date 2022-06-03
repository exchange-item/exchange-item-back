package com.bakooza.bakooza.Controller;

import com.bakooza.bakooza.DTO.BoardRequestDTO;
import com.bakooza.bakooza.DTO.BoardResponseDTO;
import com.bakooza.bakooza.Service.AwsS3Service;
import com.bakooza.bakooza.Service.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@ResponseBody
@RequiredArgsConstructor
public class BoardController {

    private final BoardServiceImpl boardService;
    private final AwsS3Service awsS3Service;

    // 게시글 작성
    @PostMapping("/boards")
    public void save(@RequestPart final BoardRequestDTO params, @RequestPart final List<MultipartFile> multipartFile) {
        awsS3Service.uploadFile(multipartFile, boardService.save(params));
    }

    // 게시판 조회
    @GetMapping("/boards") // 성공
    public Page<BoardResponseDTO> findAll(@RequestParam final int categoryId,
        @PageableDefault(sort = "post_id", direction = Sort.Direction.DESC) final Pageable pageable) {
        return boardService.findByCategoryId(categoryId, pageable).map(BoardResponseDTO::new);
    }

    // 게시글 수정
    @PatchMapping("/boards/{postId}")
    public void update(@PathVariable final Long postId, @RequestBody final BoardRequestDTO params) {
        boardService.update(postId, params);
    }

    // 게시글 삭제
    @DeleteMapping("/boards/{postId}")
    public int delete(@PathVariable final Long postId) {
        return boardService.delete(postId);
    }

    // 게시글 검색
    @GetMapping("/boards/search") // 성공
    public Page<BoardResponseDTO> search(@RequestParam final String keyword,
        @PageableDefault(sort = "post_id", direction = Sort.Direction.DESC) final Pageable pageable) {
        return boardService.search(keyword, pageable).map(BoardResponseDTO::new);
    }

    // 게시글 조회
    @GetMapping("/posts/{postId}")
    public BoardResponseDTO findById(@PathVariable final Long postId) {
        return boardService.findById(postId);
    }

    // 만료된 게시글 삭제 추후 스케쥴링
//    @DeleteMapping("/delete")
//    public int deleteExpiredPost(){
//        return boardService.deleteExpiredPost();
//    }
}