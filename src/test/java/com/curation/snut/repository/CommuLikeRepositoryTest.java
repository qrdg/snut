package com.curation.snut.repository;

import com.curation.snut.entity.Member;
import com.curation.snut.entity.community.Community;
import com.curation.snut.entity.community.CommunityLike;
import com.curation.snut.repository.community.CommuLikeRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommuLikeRepositoryTest {
    @Autowired
    CommuLikeRepository commuLikeRepository;

    @Test
    public void insertDummy() {
        CommunityLike communityLike = CommunityLike.builder()
                .member(Member.builder().email("123@123.123").build())
                .community(Community.builder().no(3l).build())
                .build();
        commuLikeRepository.save(communityLike);
    }
}
