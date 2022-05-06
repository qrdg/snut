package com.curation.snut.repository;

import com.curation.snut.entity.MemberCollectionLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MemberCurationLikeRepository extends JpaRepository<MemberCollectionLike, Long> {

    @Modifying
    @Query(value = "INSERT INTO member_curation_like(curation_curation_no, member_email) VALUES(:mcuId, :email)", nativeQuery = true)
    void likes(Long mcuId, String email);

    @Modifying
    @Query(value = "DELETE FROM member_curation_like WHERE curation_curation_no = :mcuId AND member_email = :email", nativeQuery = true)
    void unlikes(Long mcuId, String email);

    @Query("select cu.curation.curationNo from MemberCurationLike cu where cu.member.email = :email and cu.curation.curationNo = :no ")
    Long findCurationByEmailAndCurationNo(Long no, String email);

}
