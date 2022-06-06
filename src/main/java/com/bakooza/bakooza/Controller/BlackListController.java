package com.bakooza.bakooza.Controller;

import com.bakooza.bakooza.DTO.BlackListRequestDTO;
import com.bakooza.bakooza.DTO.BlackListJoinMembershipDTO;
import com.bakooza.bakooza.Service.BlackListServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/blacklist")
@ResponseBody
@RequiredArgsConstructor
public class BlackListController {

    private final BlackListServiceImpl blacklistService;

    @PostMapping() // 특정 유저 차단
    public int addBlackList(@RequestBody BlackListRequestDTO params) {
        return blacklistService.addBlackList(params);
    }

    @DeleteMapping() // 차단 해제
    public int deleteBlackList(@RequestParam int blackListId) {
        return blacklistService.deleteBlackList(blackListId);
    }


    @GetMapping() // 차단 목록 조회
    public Page<BlackListJoinMembershipDTO> findBlackList(@RequestParam Long memberId,
        @PageableDefault(sort = "blacklist_id", direction = Sort.Direction.DESC) final Pageable pageable) {
        return blacklistService.findBlackList(memberId, pageable);
    }

}
