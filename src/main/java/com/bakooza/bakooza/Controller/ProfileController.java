package com.bakooza.bakooza.Controller;

import com.bakooza.bakooza.Service.JwtUtils;
import com.bakooza.bakooza.Service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProfileController {
    private final MemberService ms;
    private final JwtUtils jwtUtils;

    public ProfileController(MemberService ms, JwtUtils jwtUtils) {
        this.ms = ms;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/profile")
    public Map<String, Object> Profile(@RequestHeader(value = "token") String token) throws SQLException {

        log.info(token);
        if(jwtUtils.validateToken(token)) {
            log.info("검증완료");
            String nickName = "";
            String email = "";
            String profileImage = "";

            String id = jwtUtils.getIdFromToken(token);

            nickName = String.valueOf(ms.findMemberById(Long.parseLong(id)).get().getNickname());
            email = String.valueOf(ms.findMemberById(Long.parseLong(id)).get().getEmail());
            profileImage = String.valueOf(ms.findMemberById(Long.parseLong(id)).get().getProfileImage());


            Map<String, Object> result = new HashMap<>();
            result.put("nickName", nickName);
            result.put("email", email);
            result.put("profileImage", profileImage);

            return result;
        }
        else {
            Map<String, Object> fail = new HashMap<>();
            fail.put("fail", "fail");

            return fail;
        }
    }
}
