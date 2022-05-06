package com.curation.snut.repository;

import java.util.Optional;

import com.curation.snut.entity.Member;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
    @EntityGraph(attributePaths = { "roleSet" }, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Member> findByEmail(String email);

    @EntityGraph(attributePaths = { "roleSet" }, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Member> findByNickName(String nickName);
    }
