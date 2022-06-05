package com.bakooza.bakooza.Repository;

import com.bakooza.bakooza.DTO.MsgDTO;
import com.bakooza.bakooza.DTO.SendMsgDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ChatMessageRepository {
    private final EntityManager em;

    public ChatMessageRepository(EntityManager em) {
        this.em = em;
    }

    public void save(MsgDTO msgDTO) {
        em.persist(msgDTO);
    }
}
