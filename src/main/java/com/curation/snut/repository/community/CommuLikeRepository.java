package com.curation.snut.repository.community;

import com.curation.snut.entity.community.CommunityLike;
import com.curation.snut.idclass.CommunityLikeId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommuLikeRepository extends JpaRepository<CommunityLike, CommunityLikeId> {

}
