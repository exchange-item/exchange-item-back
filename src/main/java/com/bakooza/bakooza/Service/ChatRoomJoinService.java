package com.bakooza.bakooza.Service;

import com.bakooza.bakooza.Entity.ChatRoom;
import com.bakooza.bakooza.Entity.ChatRoomJoin;
import com.bakooza.bakooza.Repository.ChatRoomJoinRepository;
import com.bakooza.bakooza.Repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@RequiredArgsConstructor
public class ChatRoomJoinService {
    private final ChatRoomJoinRepository chatRoomJoinRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberService memberService;

    static int roomId = 0;

    public Integer newRoom(Long user1, Long user2) {
        Integer createdRoomId = check(user1, user2);

        if(createdRoomId != 0) {
            return createdRoomId;
        }
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setId(++roomId);
        ChatRoom newChatRoom = chatRoomRepository.save(chatRoom);

        createRoom(user1, newChatRoom);
        createRoom(user2, newChatRoom);

        return newChatRoom.getId();
    }

    public Integer check(Long user1, Long user2) {
        List<ChatRoomJoin> listFirst = chatRoomJoinRepository.findByMemberId(user1);
        Set<ChatRoom> setFirst = new HashSet<>();
        for(ChatRoomJoin chatRoomJoin : listFirst) {
            setFirst.add(chatRoomJoin.getChatRoom());
        }

        List<ChatRoomJoin> listSecond = chatRoomJoinRepository.findByMemberId(user2);
        for(ChatRoomJoin chatRoomJoin : listSecond) {
            if(setFirst.contains(chatRoomJoin.getChatRoom())) {
                return chatRoomJoin.getChatRoom().getId();
            }
        }

        return 0;
    }

    public void createRoom(Long user, ChatRoom chatRoom) {
        ChatRoomJoin chatRoomJoin = new ChatRoomJoin();
        chatRoomJoin.setChatRoom(chatRoom);
        chatRoomJoin.setMember(memberService.findMemberById(user).get());
    }

    public List<ChatRoomJoin> findByChatRoom(ChatRoom chatRoom) {
        return chatRoomJoinRepository.findByChatRoom(chatRoom);
    }
}
