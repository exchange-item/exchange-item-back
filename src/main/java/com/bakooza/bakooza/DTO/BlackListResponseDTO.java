package com.bakooza.bakooza.DTO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlackListResponseDTO {

    private int blackListId;
    private String nickname;
}
