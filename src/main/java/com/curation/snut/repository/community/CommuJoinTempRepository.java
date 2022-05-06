package com.curation.snut.repository.community;

import java.util.List;

import com.curation.snut.entity.community.CommuJoinTemp;
import com.curation.snut.idclass.CommuJoinTempId;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.transaction.annotation.Transactional;

public interface CommuJoinTempRepository extends JpaRepository<CommuJoinTemp, CommuJoinTempId> {

    // 신청자 목록
    @EntityGraph(attributePaths = { "tMember", "tCommunity" }, type = EntityGraphType.LOAD)
    @Query("select cjt from CommuJoinTemp cjt where cjt.tCommunity.creater.email =:memberEmail")
    List<CommuJoinTemp> joinAlert(String memberEmail);

    @Query("select cjt.tCommunity.no from CommuJoinTemp cjt where cjt.tMember.email = :email")
    Long findRejectMember(String email);

    @Query("select cjt.tCommunity.no from CommuJoinTemp cjt where cjt.tCommunity.no = :no")
    Long findRejectCommunityNo(Long no);

    @Transactional
    @Modifying
    @Query("delete from CommuJoinTemp cjt where cjt.tCommunity.no = :no")
    void deleteByCommunityNo(Long no);

}
