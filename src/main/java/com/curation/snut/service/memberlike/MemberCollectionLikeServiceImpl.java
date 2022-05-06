package com.curation.snut.service.memberlike;

import com.curation.snut.entity.Member;
import com.curation.snut.repository.MemberCollectionLikeRepository;
import com.curation.snut.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberCollectionLikeServiceImpl implements MemberCollectionLikeService {

    private final MemberCollectionLikeRepository memberCollectionLikeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void likes(Long mcolId, String email) {
        Optional<Member> member = memberRepository.findById(email);
        memberCollectionLikeRepository.likes(mcolId, member.get().getEmail());
    }
    @Transactional
    @Override
    public void unlikes(Long mcolId, String email) {
        Optional<Member> member = memberRepository.findById(email);
        memberCollectionLikeRepository.unlikes(mcolId, member.get().getEmail());
    }
}
