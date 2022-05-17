package com.curation.snut.service.community;

import java.util.List;

import com.curation.snut.dto.community.CommunityDTO;
import com.curation.snut.entity.Member;
import com.curation.snut.entity.community.Community;

public interface CommunityService {

    void delete(Long id);

    public List<CommunityDTO> findMyCommu(String email);

    public List<CommunityDTO> CommunityList();

    public List<CommunityDTO> communityListWithCnt();

    public List<CommunityDTO> searchTitle(String searchTitle);

    void write(CommunityDTO communityDTO);

    default Community dtoToEntity(CommunityDTO dto) {
        Member member = Member.builder()
                .email(dto.getCreater().getEmail())
                .build();

        Community community = Community.builder()
                .no(dto.getNo())
                .title(dto.getTitle())
                .thumbnail(dto.getThumbnail())
                .creater(member)
                .build();

        return community;
    }

    default CommunityDTO entityToDTO(Community community) {
        CommunityDTO communityDTO = CommunityDTO.builder()
                .no(community.getNo())
                .title(community.getTitle())
                .thumbnail(community.getThumbnail())
                .creater(community.getCreater())
                .regDate(community.getRegDate())
                .modDate(community.getModDate())
                .build();

        return communityDTO;
    }

    default CommunityDTO entityToDTO(Community community, Long replyCount) {
        CommunityDTO communityDTO = CommunityDTO.builder()
                .no(community.getNo())
                .title(community.getTitle())
                .thumbnail(community.getThumbnail())
                .creater(community.getCreater())
                .replyCount(replyCount.intValue())
                .regDate(community.getRegDate())
                .modDate(community.getModDate())
                .build();

        return communityDTO;
    }
}
