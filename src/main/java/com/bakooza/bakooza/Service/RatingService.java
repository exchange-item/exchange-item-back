package com.bakooza.bakooza.Service;

import com.bakooza.bakooza.Repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RatingService {

    private RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public boolean isRating(Long fromMemberId, Long toMemberId) {
        return ratingRepository.isRating(fromMemberId, toMemberId);
    }

    public void rating(Long fromMemberId, Long toMemberId, int grade) {
        ratingRepository.rating(fromMemberId, toMemberId, grade);
    }

    public void updateRating(Long memberId, int newRating) {
        ratingRepository.updateRating(memberId, newRating);
    }

    public Integer getSumRating(Long toMemberId) {
        return ratingRepository.getSumRating(toMemberId);
    }

    public Integer getCntRating(Long toMemberId) {
        return ratingRepository.getCntRating(toMemberId);
    }

}
