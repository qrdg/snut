package com.curation.snut.repository.community;

import java.util.List;

import com.curation.snut.entity.community.CommuJoinTemp;
import com.curation.snut.idclass.CommuJoinTempId;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

public interface CommuJoinTempRepository extends JpaRepository<CommuJoinTemp, CommuJoinTempId> {

    // 신청자 목록
    @EntityGraph(attributePaths = { "tMember", "tCommunity" }, type = EntityGraphType.LOAD)
    @Query("select cjt from CommuJoinTemp cjt where cjt.tCommunity.creater.email =:memberEmail")
    List<CommuJoinTemp> joinAlert(String memberEmail);
}
