package com.bakooza.bakooza.Repository;

import com.bakooza.bakooza.Entity.ChatRoom;
import com.bakooza.bakooza.Entity.ChatRoomJoin;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ChatRoomJoinRepository {

    private final EntityManager em;

    public ChatRoomJoinRepository(EntityManager em) {
        this.em = em;
    }

    public List<ChatRoomJoin> findByMemberId(Long user) {
        List<ChatRoomJoin> result = em.createQuery("select c from ChatRoomJoin c where c.memberId= :user", ChatRoomJoin.class)
                .setParameter("user", user)
                .getResultList();

        return result;

    }

    public List<ChatRoomJoin> findByChatRoom(ChatRoom chatRoom) {
        Integer chatRoomId = chatRoom.getId();
        List<ChatRoomJoin> result = em.createQuery("select c from ChatRoomJoin c where c.roomId= :chatRoomId", ChatRoomJoin.class)
                .setParameter("chatRoomId", chatRoomId)
                .getResultList();

        return result;
    }
}
