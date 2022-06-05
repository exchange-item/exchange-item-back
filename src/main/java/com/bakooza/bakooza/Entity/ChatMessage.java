package com.bakooza.bakooza.Entity;

import com.bakooza.bakooza.Domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="message_id")
    private Integer id;
    private String message;
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name="room_id")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name="id")
    private Member writer;
}
