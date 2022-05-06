package com.curation.snut.service;

import com.curation.snut.dto.MemberDTO;
import com.curation.snut.entity.Member;
import com.curation.snut.repository.MemberRepository;
import com.curation.snut.security.role.MemberRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(Map body) {
        Member member = Member.builder()
                .email(body.get("email").toString())
                .pw(passwordEncoder.encode(body.get("pw").toString()))
                .name(body.get("name").toString())
                .gender(body.get("gender").toString())
                .nickName(body.get("nickName").toString())
                .birth(body.get("birth").toString())
                .mobile(body.get("mobile").toString())
                .build();
        member.addMemberRole(MemberRole.USER);

        memberRepository.save(member);
    }

    @Override
    public void updateMemberDTO(MemberDTO memberDTO) {
        log.info("updateMemberDTO..MemberDTO:" + memberDTO);
        memberDTO.setPw(passwordEncoder.encode(memberDTO.getPw()));
        Member member = dtoToEntity(memberDTO);
        memberRepository.save(member);
        log.info("member>>" + member);
    }
}
