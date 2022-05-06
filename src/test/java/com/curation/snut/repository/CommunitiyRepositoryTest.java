package com.curation.snut.repository;

import com.curation.snut.entity.Member;
import com.curation.snut.entity.community.Community;
import com.curation.snut.repository.community.CommentRepository;
import com.curation.snut.repository.community.CommunityRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommunitiyRepositoryTest {

    @Autowired
    CommunityRepository communitiyRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void deleteCommentTest() {
        Long CommentLists = commentRepository.searchComment(1L);
        System.out.println(CommentLists);
        commentRepository.deleteById(1L);
        commentRepository.deleteReply(1L);
    }

    @Test
    public void insertCommunity() {
        Community community = Community.builder()
                .title("asd")
                .thumbnail("dummy")
                .creater(Member.builder().email("123@123.123").build())
                .build();
        communitiyRepository.save(community);
    }

    @Test
    public void insertCommunity2() {
        Community community = Community.builder()
                .title("자동차")
                .thumbnail("dummy")
                .creater(Member.builder().email("456@456.456").build())
                .build();
        communitiyRepository.save(community);
    }

}
