package com.curation.snut.repository;

import java.util.Optional;

import com.curation.snut.entity.Member;
import com.curation.snut.security.role.MemberRole;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertMember() {
        Member member = Member.builder()
                .email("456@456.456")
                .name("456")
                .pw(passwordEncoder.encode("456"))
                .mobile("456456")
                .birth("456")
                .gender("male")
                .nickName("Def")
                .build();
        member.addMemberRole(MemberRole.USER);
        memberRepository.save(member);
    }

    @Test
    public void insertMember2() {
        Member member = Member.builder()
                .email("123@123.123")
                .name("123")
                .pw(passwordEncoder.encode("123"))
                .mobile("123")
                .birth("123")
                .gender("female")
                .nickName("Abc")
                .build();
        member.addMemberRole(MemberRole.USER);
        memberRepository.save(member);
    }

    @Test
    public void testRead() {
        Optional<Member> result = memberRepository.findByEmail("456@456.456");
        Member member = result.get();
        System.out.println(member);
    }

}
