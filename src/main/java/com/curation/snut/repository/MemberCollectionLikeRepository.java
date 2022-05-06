package com.curation.snut.repository;

import com.curation.snut.entity.MemberCollectionLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MemberCollectionLikeRepository extends JpaRepository<MemberCollectionLike, Long> {

    @Modifying
    @Query(value = "INSERT INTO member_collection_like(snut_collection_collection_no, member_email) VALUES(:mcolId, :email)", nativeQuery = true)
    void likes(Long mcolId, String email);

    @Modifying
    @Query(value = "DELETE FROM member_collection_like WHERE snut_collection_collection_no = :mcolId AND member_email = :email", nativeQuery = true)
    void unlikes(Long mcolId, String email);

    @Query("select col.snutCollection.collectionNo from MemberCollectionLike col where col.member.email = :email and col.snutCollection.collectionNo = :no")
    Long findCollectionByEmailAndCollectionNo(Long no, String email);
}
