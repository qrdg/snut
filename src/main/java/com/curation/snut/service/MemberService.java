package com.curation.snut.service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.curation.snut.dto.MemberDTO;
import com.curation.snut.entity.Member;
import com.curation.snut.security.role.MemberRole;

public interface MemberService {

    void register(Map body);

    void updateMemberDTO(MemberDTO memberDTO);

    default Member dtoToEntity(MemberDTO memberDTO) {
        Member member = Member.builder()
                .email(memberDTO.getEmail())
                .pw(memberDTO.getPw())
                .name(memberDTO.getName())
                .nickName(memberDTO.getNickname())
                .mobile(memberDTO.getMobile())
                .birth(memberDTO.getBirth())
                .gender(memberDTO.getGender())
                .roleSet(memberDTO.getRoleSet().stream().map(
                        new Function<String, MemberRole>() {
                            @Override
                            public MemberRole apply(String t) {
                                if (t.equals("ROLE_GUEST"))
                                    return MemberRole.USER;
                                else if (t.equals("ROLE_MEMBER"))
                                    return MemberRole.MANAGER;
                                else if (t.equals("ROLE_ADMIN"))
                                    return MemberRole.ADMIN;
                                else
                                    return MemberRole.USER;
                            }
                        }).collect(Collectors.toSet()))
                .build();

        return member;
    }

    default MemberDTO entityToDTO(Member member) {
        MemberDTO memberDTO = MemberDTO.builder()
                .email(member.getEmail())
                .name(member.getName())
                .mobile(member.getMobile())
                .roleSet(member.getRoleSet().stream().map(
                        role -> new String("ROLE_" + role.name()))
                        .collect(Collectors.toSet()))
                .regDate(member.getRegDate())
                .modDate(member.getModDate())
                .build();
        return memberDTO;
    }
}
