package com.curation.snut.service.community;

import java.util.List;
import java.util.Map;

import com.curation.snut.dto.community.CommuJoinDTO;
import com.curation.snut.entity.Member;
import com.curation.snut.entity.community.CommuJoinTemp;
import com.curation.snut.entity.community.Community;
import com.curation.snut.idclass.CommuJoinId;
import com.curation.snut.idclass.CommuJoinTempId;
import com.curation.snut.repository.community.CommuJoinRepository;
import com.curation.snut.repository.community.CommuJoinTempRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommuJoinTempServiceImpl implements CommuJoinTempService {

        private final CommuJoinTempRepository commuJoinTempRepository;
        private final CommuJoinRepository commuJoinRepository;

        @Override
        public String joinApply(CommuJoinDTO commuJoinDTO) {
                CommuJoinTempId id = CommuJoinTempId.builder().tMember(commuJoinDTO.getMember().getEmail())
                                .tCommunity(commuJoinDTO.getCommunity().getNo()).build();
                CommuJoinId id2 = CommuJoinId.builder().jMember(commuJoinDTO.getMember().getEmail())
                                .jCommunity(commuJoinDTO.getCommunity().getNo()).build();
                if (commuJoinTempRepository.findById(id).isPresent()) {
                        return "중복신청";
                } else if (commuJoinRepository.findById(id2).isPresent()) {
                        return "이미 가입";
                } else {
                        Member member = Member.builder()
                                        .email(commuJoinDTO.getMember().getEmail())
                                        .build();
                        Community community = Community.builder()
                                        .no(commuJoinDTO.getCommunity().getNo())
                                        .build();

                        CommuJoinTemp commuJoinTemp = CommuJoinTemp.builder()
                                        .tMember(member)
                                        .tCommunity(community).build();
                        commuJoinTempRepository.save(commuJoinTemp);
                        return "신청 완료";
                }

        }

        @Transactional
        @Override
        public void joinAcceptAfterProcess(CommuJoinDTO commuJoinDTO) {
                CommuJoinTempId id = CommuJoinTempId.builder().tMember(commuJoinDTO.getMember().getEmail())
                                .tCommunity(commuJoinDTO.getCommunity().getNo()).build();
                commuJoinTempRepository.deleteById(id);

        }

        @Override
        public List<CommuJoinTemp> joinAlertList(String memberEmail) {
                return commuJoinTempRepository.joinAlert(memberEmail);
        }

}
