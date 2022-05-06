package com.curation.snut.service.memberlike;

import com.curation.snut.entity.Member;
import com.curation.snut.repository.MemberCurationLikeRepository;
import com.curation.snut.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberCurationLikeServiceImpl implements MemberCurationLikeService {

    private final MemberCurationLikeRepository memberCurationLikeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void likes(Long mcuId, String email) {
        Optional<Member> member = memberRepository.findById(email);
        memberCurationLikeRepository.likes(mcuId, member.get().getEmail());
    }
    @Transactional
    @Override
    public void unlikes(Long mcuId, String email) {
        Optional<Member> member = memberRepository.findById(email);
        memberCurationLikeRepository.unlikes(mcuId, member.get().getEmail());
    }
}
