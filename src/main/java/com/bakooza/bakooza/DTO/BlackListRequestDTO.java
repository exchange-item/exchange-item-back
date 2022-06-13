package com.bakooza.bakooza.DTO;

import com.bakooza.bakooza.Entity.Blacklist;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlackListRequestDTO {

    private int blackListId;
    private Long memberId;
    private Long blackListMemeberId;

    public Blacklist toEntity() {
        return Blacklist.builder()
            .blackListId(blackListId)
            .memberId(memberId)
            .blackListMemeberId(blackListMemeberId)
            .build();
    }
}
