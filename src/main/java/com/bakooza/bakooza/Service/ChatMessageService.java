package com.bakooza.bakooza.Service;

import com.bakooza.bakooza.DTO.MsgDTO;
import com.bakooza.bakooza.DTO.SendMsgDTO;
import com.bakooza.bakooza.Repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    public void save(MsgDTO msgDTO) {
        chatMessageRepository.save(msgDTO);
    }
}
