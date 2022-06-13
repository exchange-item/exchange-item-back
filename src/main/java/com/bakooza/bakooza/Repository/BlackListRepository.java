package com.bakooza.bakooza.Repository;

import com.bakooza.bakooza.DTO.BlackListJoinMembershipDTO;
import com.bakooza.bakooza.Entity.Blacklist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlackListRepository extends JpaRepository<Blacklist, Integer> {

    @Query(value = "SELECT b.blacklist_id, m.nickname FROM blacklist b JOIN membership m WHERE b.blacklist_member_id = m.member_id and b.member_id = :memberId", nativeQuery = true)
    Page<BlackListJoinMembershipDTO> findAllByMemberId(@Param("memberId") final Long memberId, final Pageable pageable);
}
