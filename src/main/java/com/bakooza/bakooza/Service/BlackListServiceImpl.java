package com.bakooza.bakooza.Service;

import com.bakooza.bakooza.DTO.BlackListRequestDTO;
import com.bakooza.bakooza.DTO.BlackListJoinMembershipDTO;
import com.bakooza.bakooza.Entity.Blacklist;
import com.bakooza.bakooza.Repository.BlackListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlackListServiceImpl implements BlackListService {

    private final BlackListRepository blackListRepository;

    @Override
    public int addBlackList(BlackListRequestDTO params) {
        Blacklist entity = blackListRepository.save(params.toEntity());
        return entity.getBlackListId();
    }

    @Override
    public int deleteBlackList(int blackListId) {
        blackListRepository.deleteById(blackListId);
        return blackListId;
    }

    @Override
    public Page<BlackListJoinMembershipDTO> findBlackList(Long memberId, final Pageable pageable) {
        Page<BlackListJoinMembershipDTO> blackListJoinMembershipDTO = blackListRepository.findAllByMemberId(memberId, pageable);
        System.out.println("여기야 !!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(blackListJoinMembershipDTO.toString());
        System.out.println("여기야 !!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        return blackListJoinMembershipDTO;
    }
}
