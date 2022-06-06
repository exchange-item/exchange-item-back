package com.bakooza.bakooza.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {
    private String token;
    private Long memberId;
    private int grade;
}
