package com.bakooza.bakooza.Controller;

import com.bakooza.bakooza.DTO.*;
import com.bakooza.bakooza.Entity.ChatMessage;
import com.bakooza.bakooza.Entity.ChatRoom;
import com.bakooza.bakooza.Entity.ChatRoomJoin;
import com.bakooza.bakooza.Service.ChatMessageService;
import com.bakooza.bakooza.Service.ChatRoomJoinService;
import com.bakooza.bakooza.Service.ChatRoomService;
import com.bakooza.bakooza.Service.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/chat")
@RequiredArgsConstructor
public class ChatMessageController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final ChatRoomJoinService chatRoomJoinService;
    private final JwtUtils jwtUtils;

    static int messageId = 0;

    @PostMapping("/room/new-chat")
    public ResponseEntity<Object> newChat(@RequestBody ChatUserDTO chatUserDTO) {
        if(jwtUtils.validateToken(chatUserDTO.getToken())) {
            String strMemberId = jwtUtils.getIdFromToken(chatUserDTO.getToken());
            Long myMemberId = Long.parseLong(strMemberId);
            Long memberId = chatUserDTO.getMemberId();

            Integer chatRoomId = chatRoomJoinService.newRoom(myMemberId, memberId);

            ChatRoomIdDTO chatRoomIdDTO = new ChatRoomIdDTO();
            chatRoomIdDTO.setChatRoomId(chatRoomId);


            return new ResponseEntity<>(chatRoomIdDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Fail", HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<Object> goChat(@PathVariable("roomId") Integer roomId, @RequestHeader(value="token", required = false) String token) {


        String strMemberId = jwtUtils.getIdFromToken(token);
        Long myMemberId = Long.parseLong(strMemberId);

        ChatRoom chatRoom = chatRoomService.findById(roomId);
        List<ChatMessage> messages = chatRoom.getMessages();
        Collections.reverse(messages);

        List<ChatRoomJoin> list = chatRoomJoinService.findByChatRoom(chatRoom);
        ChatDetailDTO chatDetailDTO = new ChatDetailDTO();
        chatDetailDTO.setMyMemberId(myMemberId);
        chatDetailDTO.setMessages(messages);
        for(ChatRoomJoin join : list) {
            if(join.getMember().getId().equals(myMemberId) == false) {
                Long yourMemberId = join.getMember().getId();
                String yourNickname = join.getMember().getNickname();
                chatDetailDTO.setYourMemberId(yourMemberId);
                chatDetailDTO.setYourNickname(yourNickname);
            }
            else {
                String myNickname = join.getMember().getNickname();
                chatDetailDTO.setMyNickname(myNickname);
            }
        }

        return new ResponseEntity<>(chatDetailDTO, HttpStatus.OK);
    }

    @PostMapping("/chat/send")
    public void sendMsg(@RequestBody SendMsgDTO sendMsgDTO) {
        Long myMemberId = Long.parseLong(jwtUtils.getIdFromToken(sendMsgDTO.getToken()));
        MsgDTO msgDTO = new MsgDTO();
        msgDTO.setMemberId(myMemberId);
        msgDTO.setMessageId(++messageId);
        msgDTO.setRoomId(sendMsgDTO.getRoomId());
        chatMessageService.save(msgDTO);
        simpMessagingTemplate.convertAndSend("/topic/" + sendMsgDTO.getMemberId(), sendMsgDTO);
    }

}
