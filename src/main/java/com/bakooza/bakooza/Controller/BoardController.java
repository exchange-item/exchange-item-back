package com.bakooza.bakooza.Controller;

import com.bakooza.bakooza.DTO.BoardRequestDTO;
import com.bakooza.bakooza.DTO.BoardResponseDTO;
import com.bakooza.bakooza.DTO.ImageResponseDTO;
import com.bakooza.bakooza.Service.AwsS3Service;
import com.bakooza.bakooza.Service.BoardServiceImpl;
import com.bakooza.bakooza.Service.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/boards")
@ResponseBody
@RequiredArgsConstructor
public class BoardController {

    private final BoardServiceImpl boardService;
    private final AwsS3Service awsS3Service;
    private final JwtUtils jwtUtils;

    // 게시글 작성
    @PostMapping()
    public ResponseEntity<Object> save(@RequestHeader(value = "token") String token, @RequestPart final BoardRequestDTO params) {
        Long postId = boardService.save(params);
        return findById(postId);
    }

    // 게시판 조회
    @GetMapping()
    public Page<BoardResponseDTO> findAll(@RequestParam final int categoryId,
                                          @PageableDefault(sort = "post_id", direction = Sort.Direction.DESC) final Pageable pageable) {
        return boardService.findByCategoryId(categoryId, pageable).map(BoardResponseDTO::new);
    }

    // 게시글 수정
    @PatchMapping("/{postId}")
    public ResponseEntity<Object> update(@RequestHeader(value = "token") String token, @PathVariable final Long postId, @RequestPart final BoardRequestDTO params, @RequestPart(required = false) final @NotNull List<MultipartFile> multipartFile) {
        if (jwtUtils.validateToken(token)) {
            boardService.update(postId, params);
            boolean flag = false;
            for (MultipartFile file : multipartFile) {
                if (file.isEmpty()) {
                    flag = true;
                }
            }

            List<ImageResponseDTO> entity = boardService.findByPostId(postId); // S3의 경로를 찾아오고
            for (ImageResponseDTO imageResponseDTO : entity) {
                awsS3Service.deleteFile(imageResponseDTO.getImagePath()); // S3에서 삭제
            }

            if (!flag) { // 새로 업로드하려는 파일이 있다면 기존의 파일 삭제 후 추가
                awsS3Service.uploadFile(multipartFile, postId); // 새 파일 추가
            }

            return findById(postId);
        } else {
            Map<String, Object> fail = new HashMap<>();
            fail.put("fail", "fail");

            return new ResponseEntity<>(fail, HttpStatus.UNAUTHORIZED);
        }
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Object> delete(@RequestHeader(value = "token") String token, @PathVariable final Long postId) {
        if (jwtUtils.validateToken(token)) {
            List<ImageResponseDTO> entity = boardService.findByPostId(postId); // S3의 경로를 먼저 찾아오고
            boardService.delete(postId); // DB에서 삭제
            for (ImageResponseDTO imageResponseDTO : entity) {
                awsS3Service.deleteFile(imageResponseDTO.getImagePath()); // S3에서도 삭제
            }
            return new ResponseEntity<>(postId, HttpStatus.UNAUTHORIZED);
        } else {
            Map<String, Object> fail = new HashMap<>();
            fail.put("fail", "fail");

            return new ResponseEntity<>(fail, HttpStatus.UNAUTHORIZED);
        }
    }

    // 게시글 검색
    @GetMapping("/search")
    public Page<BoardResponseDTO> search(@RequestParam final String keyword,
                                         @PageableDefault(sort = "post_id", direction = Sort.Direction.DESC) final Pageable pageable) {
        return boardService.search(keyword, pageable).map(BoardResponseDTO::new);
    }

    // 게시글 조회
    @GetMapping("/{postId}")
    public ResponseEntity<Object> findById(@PathVariable final Long postId) {
        Map<String, Object> map = new HashMap<>();
        map.put("post", boardService.findById(postId));
        map.put("image", boardService.findByPostId(postId));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // 만료된 게시글 삭제 추후 스케쥴링
//    @DeleteMapping("/delete")
//    public int deleteExpiredPost(){
//        return boardService.deleteExpiredPost();
//    }
}