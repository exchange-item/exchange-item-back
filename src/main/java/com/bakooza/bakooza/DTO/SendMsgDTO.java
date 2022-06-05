package com.bakooza.bakooza.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SendMsgDTO {

    private String token;
    private Long memberId;
    private Integer roomId;
    private String contents;
}
