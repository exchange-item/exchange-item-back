package com.bakooza.bakooza.Controller;

import com.bakooza.bakooza.DTO.RatingDTO;
import com.bakooza.bakooza.Service.JwtUtils;
import com.bakooza.bakooza.Service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RatingController {
    private final JwtUtils jwtUtils;
    private final RatingService ratingService;

    public RatingController(JwtUtils jwtUtils, RatingService ratingService) {
        this.jwtUtils = jwtUtils;
        this.ratingService = ratingService;
    }


    @PostMapping()
    public ResponseEntity<Object> ratingMember(@RequestBody RatingDTO ratingDTO) {
        if(ratingDTO.getToken().equals("2250260102")) {
            log.info("인증완료");
            Long fromMemberId = 0L;
            Long toMemberId = 0L;
            fromMemberId = Long.parseLong(ratingDTO.getToken());
            toMemberId = ratingDTO.getMemberId();

            log.info("hi");
            if(ratingService.isRating(fromMemberId, toMemberId)) {
                // 평가 저장
                int grade = ratingDTO.getGrade();
                log.info("sasd");
                ratingService.rating(fromMemberId, toMemberId, grade);

                log.info("점수 저장");
                int sumRating = ratingService.getSumRating(toMemberId);
                int cntRating = ratingService.getCntRating(toMemberId);
                int newRating = sumRating / cntRating;

                ratingService.updateRating(toMemberId, newRating);


                return new ResponseEntity<>("Success", HttpStatus.OK);
            } else {
                // 평가 불가
                log.info("already rate!");
                return new ResponseEntity<>("Fail", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Fail", HttpStatus.BAD_REQUEST);
        }
    }
}
