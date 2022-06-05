package com.bakooza.bakooza.DTO;

import com.bakooza.bakooza.Entity.ChatMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ChatDetailDTO {
    private Long myMemberId;
    private Long yourMemberId;
    private String myNickname;
    private String yourNickname;
    private List<ChatMessage> messages;
}
