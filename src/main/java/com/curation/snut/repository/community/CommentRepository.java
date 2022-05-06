package com.curation.snut.repository.community;

import java.util.List;

import com.curation.snut.dto.community.CommentDTO;
import com.curation.snut.entity.community.CommunityComment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends JpaRepository<CommunityComment, Long> {

    @EntityGraph(attributePaths = { "writer", "communityName" }, type = EntityGraphType.LOAD) // 연관관계 설정해줘야함
    @Query("select c from CommunityComment c where c.communityName.no = :no and announcement = 0 order by if (isnull(parentNo), cno, parentNo)")
    Page<CommunityComment> list(Pageable pageable, Long no);

    @EntityGraph(attributePaths = { "writer", "communityName" }, type = EntityGraphType.LOAD) // 연관관계 설정해줘야함
    @Query("select c from CommunityComment c where c.communityName.no = :no and announcement = 1 order by if (isnull(parentNo), cno, parentNo)")
    Page<CommunityComment> anlist(Pageable pageable, Long no);

    @Modifying
    @Transactional
    @Query("delete from CommunityComment c where c.parentNo = :id")
    void deleteReply(Long id);

    @Query("select cno from CommunityComment c where c.cno= :id and c.parentNo is null")
    Long searchComment(Long id);

    @Modifying
    @Transactional
    @Query("delete from CommunityComment c where c.communityName.no = :no")
    void deleteAllCommunutyComment(Long no);

    @Query("select COUNT(c) from CommunityComment c where c.communityName.no = :no")
    Long commentCnt(Long no);

    @EntityGraph(attributePaths = { "writer", "communityName" }, type = EntityGraphType.LOAD)
    @Query("select c from CommunityComment c where c.cno =:dtoCno ")
    CommunityComment findCno(Long dtoCno);

}
