package com.curation.snut.service.community;

import com.curation.snut.dto.community.CommuLikeDTO;
import com.curation.snut.entity.Member;
import com.curation.snut.entity.community.Community;
import com.curation.snut.entity.community.CommunityLike;

public interface CommuLikeService {

        void reg(CommuLikeDTO commuLikeDTO);

        default CommunityLike dtoToEntity(CommuLikeDTO dto) {
                Member member = Member.builder()
                                .email(dto.getMember().getEmail())
                                .build();
                Community community = Community.builder()
                                .no(dto.getCommunity().getNo())
                                .build();

                CommunityLike communityLike = CommunityLike.builder()
                                .member(member)
                                .community(community)
                                .build();
                return communityLike;
        }

        default CommuLikeDTO entityToDTO(CommunityLike Like) {

                CommuLikeDTO dto = CommuLikeDTO.builder()
                                .member(Like.getMember())
                                .community(Like.getCommunity())
                                .build();

                return dto;
        }
}
