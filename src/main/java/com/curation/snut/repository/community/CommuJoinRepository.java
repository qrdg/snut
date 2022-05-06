package com.curation.snut.repository.community;

import java.util.List;
import java.util.Optional;

import com.curation.snut.entity.Member;
import com.curation.snut.entity.community.CommuJoin;
import com.curation.snut.idclass.CommuJoinId;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

public interface CommuJoinRepository extends JpaRepository<CommuJoin, CommuJoinId> {

    @EntityGraph(attributePaths = { "jMember", "jCommunity" }, type = EntityGraphType.LOAD)
    @Query("select cj from CommuJoin cj where cj.jCommunity.no =:no and cj.jMember.email =:email")
    Optional<CommuJoin> findWriteAuthority(Long no, String email);

    @EntityGraph(attributePaths = { "jMember", "jCommunity" }, type = EntityGraphType.LOAD)
    @Query("select cj from CommuJoin cj where cj.jCommunity.creater.email =:email")
    List<CommuJoin> findMyCommu(String email);

    @EntityGraph(attributePaths = { "jMember", "jCommunity" }, type = EntityGraphType.LOAD)
    @Query("select cj from CommuJoin cj where cj.jMember.email =:email")
    List<CommuJoin> findJoinCommu(String email);

}
