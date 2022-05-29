package com.bakooza.bakooza.Service;

import com.bakooza.bakooza.DTO.BlackListJoinMembershipDTO;
import com.bakooza.bakooza.DTO.BlackListRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlackListService {

    int addBlackList(BlackListRequestDTO params);

    int deleteBlackList(int blackListId);

    Page<BlackListJoinMembershipDTO> findBlackList(Long memberId, final Pageable pageable);

}
