package com.winwin.exchange.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.winwin.exchange.DTO.JwtAccessTokenDTO;
import com.winwin.exchange.Domain.Member;
import com.winwin.exchange.Service.JwtUtils;
import com.winwin.exchange.Service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@RestController
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {

    private final MemberService ms;
    private final JwtUtils jwtUtils;

    public LoginController(MemberService ms, JwtUtils jwtUtils) {
        this.ms = ms;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/oauth/kakao/login")
    public ResponseEntity<Object> kakaoCallback(@RequestParam(value = "code") String code) throws JsonProcessingException, UnsupportedEncodingException {

        log.info("authorizationCode={}", code);

        String accessToken = ms.getAccessToken(code);
        log.info("getAccessToken 진입 후 받아 온 accessToken = {}", accessToken);

        HashMap<String, Object> userInfo = ms.getUserInfo(accessToken);

        Member member = new Member();
        member.setId(Long.parseLong(userInfo.get("id").toString())-10000);
        member.setNickname(userInfo.get("nickname").toString());
        member.setEmail(userInfo.get("email").toString());
        member.setProfileImage(userInfo.get("profileImage").toString());

        Boolean isMemberPresent = ms.findMemberById(member.getId()).isPresent();

        if(isMemberPresent == false) {
            ms.join(member);
        }
        else {
            log.info("The member is already sign up");
        }

        String jwtAccessToken = jwtUtils.createJwt(member);

        HttpHeaders headers = new HttpHeaders();
        headers.set("jwtAccessToken", jwtAccessToken);
        headers.set("nickname", member.getNickname());
        headers.set("email", member.getEmail());

        log.info("headers = {}", headers);
        log.info("headers jwtAccessToken = {}", headers.get("jwtAccessToken"));

        return new ResponseEntity<>("로그인 성공", headers, HttpStatus.OK);
    }

}
