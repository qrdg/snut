package com.curation.snut.service.community;

import com.curation.snut.dto.community.CommentDTO;
import com.curation.snut.entity.Member;
import com.curation.snut.entity.community.Community;
import com.curation.snut.entity.community.CommunityComment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

        void write(CommentDTO commentDTO);

        void delete(Long id);

        void deleteAllCommunutyComment(Long no);

        Long commnetCnt(Long no);

        public Page<CommentDTO> commentList(Pageable pageable, Long no);

        public Page<CommentDTO> ancommentList(Pageable pageable, Long no);

        default CommunityComment dtoToEntity(CommentDTO dto) {

                Member member = Member.builder()
                                .email(dto.getWriter().getEmail())
                                .build();
                Community community = Community.builder()
                                .no(dto.getCommunityName().getNo())
                                .build();

                CommunityComment comment = CommunityComment.builder()
                                .cno(dto.getCno())
                                .text(dto.getText())
                                .parentNo(dto.getParentNo())
                                .announcement(dto.isAnnouncement())
                                .writer(member)
                                .communityName(community)
                                .build();
                return comment;
        }

        default CommentDTO entityToDTO(CommunityComment comment) {
                CommentDTO commentDTO = CommentDTO.builder()
                                .cno(comment.getCno())
                                .text(comment.getText())
                                .parentNo(comment.getParentNo())
                                .announcement(comment.isAnnouncement())
                                .writer(comment.getWriter())
                                .communityName(comment.getCommunityName())
                                .regDate(comment.getRegDate())
                                .modDate(comment.getModDate())
                                .build();
                return commentDTO;
        }
}
