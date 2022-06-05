package com.bakooza.bakooza.Repository;

import com.bakooza.bakooza.Entity.ChatRoom;

import javax.persistence.EntityManager;


public class ChatRoomRepository {

    private final EntityManager em;

    public ChatRoomRepository(EntityManager em) {
        this.em = em;
    }

    public ChatRoom save(ChatRoom chatRoom) {
        em.persist(chatRoom);
        return chatRoom;
    }

    public ChatRoom findById(Integer roomId) {
        ChatRoom chatRoom = em.find(ChatRoom.class, roomId);
        return chatRoom;
    }
}
