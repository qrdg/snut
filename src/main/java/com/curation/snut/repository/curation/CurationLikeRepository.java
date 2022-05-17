package com.curation.snut.repository.curation;

import com.curation.snut.entity.Member;
import com.curation.snut.entity.curation.Curation;
import com.curation.snut.entity.curation.CurationLike;
import com.curation.snut.idclass.CurationLikeId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurationLikeRepository extends JpaRepository<CurationLike, CurationLikeId> {
    CurationLike findLikesByCurationAndMember(Curation curation, Member memeber);

    // @Modifying
    // // https://moonsbeen.tistory.com/309 like repository에 value값참조
    // @Query(value = "")
    // void likes(long curationNo, String email);

    // @Modifying
    // @Query(value = "")
    // void unLikes(long curationNo, String email);
}
