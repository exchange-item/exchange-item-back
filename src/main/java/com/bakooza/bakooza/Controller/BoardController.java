package com.bakooza.bakooza.Controller;

import com.bakooza.bakooza.DTO.*;
import com.bakooza.bakooza.Service.AwsS3Service;
import com.bakooza.bakooza.Service.BoardServiceImpl;
import com.bakooza.bakooza.Service.JwtUtils;
import com.bakooza.bakooza.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BoardController {

    private final BoardServiceImpl boardService;
    private final AwsS3Service awsS3Service;
    private final JwtUtils jwtUtils;
    private final MemberService ms;

    // 게시글 작성
    @PostMapping(value = "/write" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> save(@RequestPart(value="accessToken", required = false) TokenDTO accessToken, @RequestPart(value="params") BoardRequestDTO params, @RequestPart(value="multipartFile", required = false) MultipartFile multipartFile) {
        boolean flag = false;
        Long postId = 0L;

        log.info("params = {}");
        log.info("mf={}", multipartFile.getOriginalFilename());
//        log.info("tokenasdassdasdasdasdasdasd={}", accessToken.getAccessToken());
        if (jwtUtils.validateToken(accessToken.getAccessToken())) {
//            for (MultipartFile file : multipartFile) {
//                if (file.isEmpty()) {
//                    flag = true;
//                }
//            }
//
//            if (flag) { // 이미지 파일이 첨부되어 있지 않다면
//                postId = boardService.save(params);
//            } else { // 이미지 파일이 첨부되어 있다면
//                postId = awsS3Service.uploadFile(multipartFile, boardService.save(params));
//            }
            Long memberId = Long.parseLong(jwtUtils.getIdFromToken(accessToken.getAccessToken()));
            String writer = String.valueOf(ms.findMemberById(memberId));
            BoardDTO boardDTO = new BoardDTO(params.getTitle(), params.getUserLocation(), params.getCategoryId(), params.getContent(), "writer", 123L);
            awsS3Service.uploadFile(multipartFile, boardService.save(boardDTO));
//            if (flag) { // 이미지 파일이 첨부되어 있지 않다면
//                postId = boardService.save(boardDTO);
//            } else { // 이미지 파일이 첨부되어 있다면
//                //postId = awsS3Service.uploadFile(multipartFile, boardService.save(params));
//            }
            //return findById(postId);
            return new ResponseEntity<>("success", HttpStatus.OK);

        } else {
            Map<String, Object> fail = new HashMap<>();
            fail.put("fail", "fail");

            return new ResponseEntity<>(fail, HttpStatus.UNAUTHORIZED);
        }

    }

    // 게시판 조회
//    @GetMapping("/category")
//    public Page<BoardResponseDTO> findAll(@RequestParam final int categoryId,
//                                          @PageableDefault(sort = "post_id", direction = Sort.Direction.DESC) final Pageable pageable) {
//        return boardService.findByCategoryId(categoryId, pageable).map(BoardResponseDTO::new);
//    }

    // 게시글 수정
    @PatchMapping("/{postId}")
    public ResponseEntity<Object> update(@RequestPart(value = "accessToken") String token, @PathVariable final Long postId, @RequestPart final BoardRequestDTO params, @RequestPart(required = false) final MultipartFile multipartFile) {
        if (jwtUtils.validateToken(token)) {
//            boardService.update(postId, params);
            boolean flag = multipartFile.isEmpty();
//            for (MultipartFile file : multipartFile) {
//                if (file.isEmpty()) {
//                    flag = true;
//                }
//            }

            List<ImageResponseDTO> entity = boardService.findByPostId(postId); // S3의 경로를 찾아오고
            for (ImageResponseDTO imageResponseDTO : entity) {
                awsS3Service.deleteFile(imageResponseDTO.getImagePath()); // S3에서 삭제
            }

            if (!flag) { // 새로 업로드하려는 파일이 있다면 기존의 파일 삭제 후 추가
                awsS3Service.uploadFile(multipartFile, postId); // 새 파일 추가
            }

            //return findById(postId);
            return new ResponseEntity<>("success", HttpStatus.OK);
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

     //게시글 검색
    @GetMapping("/search")
    public Page<BoardResponseDTO> search(@RequestParam final String keyword,
        @PageableDefault(sort = "post_id", direction = Sort.Direction.DESC) final Pageable pageable) {
        return boardService.search(keyword, pageable).map(BoardResponseDTO::new);
    }

     //게시글 조회
    @GetMapping("/detail/{postId}")
    public ResponseEntity<Object> findById(@PathVariable final Long postId) {
        Map<String, Object> map = new HashMap<>();
        map.put("post", boardService.findById(postId));
        map.put("image", boardService.findByPostId(postId));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //만료된 게시글 삭제 추후 스케쥴링
    @DeleteMapping("/delete")
    public int deleteExpiredPost(){
        return boardService.deleteExpiredPost();
    }

    // 만료된 게시글 삭제 추후 스케쥴링
//    @DeleteMapping("/delete")
//    public int deleteExpiredPost(){
//        return boardService.deleteExpiredPost();
//    }
}
