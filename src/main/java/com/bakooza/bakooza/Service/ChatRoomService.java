package com.bakooza.bakooza.Service;

import com.bakooza.bakooza.Entity.ChatRoom;
import com.bakooza.bakooza.Repository.ChatRoomRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;


    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }


    public ChatRoom findById(Integer chatRoomId) {
        return chatRoomRepository.findById(chatRoomId);
    }


}
