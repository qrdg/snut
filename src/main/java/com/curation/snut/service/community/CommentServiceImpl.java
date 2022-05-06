package com.curation.snut.service.community;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.curation.snut.dto.community.CommentDTO;
import com.curation.snut.entity.community.CommentAlert;
import com.curation.snut.entity.community.CommunityComment;
import com.curation.snut.repository.community.CommentAlertRepository;
import com.curation.snut.repository.community.CommentRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentAlertRepository commentAlertRepository;

    @Override
    public void write(CommentDTO commentDTO) {
        if (commentDTO.getParentNo() != null) {
            CommunityComment find = commentRepository.findCno(commentDTO.getParentNo());
            CommentDTO user = entityToDTO(find);

            String alertuser = user.getWriter().getNickName();
            String commuName = user.getCommunityName().getTitle();
            String text = user.getText();
            Long cmo = find.getCommunityName().getNo();

            CommentAlert commentAlert = CommentAlert.builder().nickName(alertuser).commuName(commuName).cmo(cmo)
                    .text(text).build();
            // commuName(커뮤니티이름)의 alertuser(유저닉네임)님의 text(글내용)에 댓글이 달렸습니다.
            commentAlertRepository.save(commentAlert);
        }
        CommunityComment communityComment = dtoToEntity(commentDTO);
        commentRepository.save(communityComment);
    }

    @Transactional
    @Override
    public Page<CommentDTO> commentList(Pageable pageable, Long no) {
        Page<CommunityComment> communityComments = commentRepository.list(pageable, no);
        List<CommentDTO> commentDTOList = communityComments.stream()
                .map(comment -> entityToDTO(comment)).collect(Collectors.toList());
        return new PageImpl<>(commentDTOList, pageable, communityComments.getTotalElements());

    }

    @Override
    public Page<CommentDTO> ancommentList(Pageable pageable, Long no) {
        Page<CommunityComment> communityComments = commentRepository.anlist(pageable, no);
        List<CommentDTO> commentDTOList = communityComments.stream()
                .map(comment -> entityToDTO(comment)).collect(Collectors.toList());
        return new PageImpl<>(commentDTOList, pageable, communityComments.getTotalElements());

    }

    @Transactional
    @Override
    public void delete(Long id) {
        Long CommentLists = commentRepository.searchComment(id);
        if (CommentLists != null) {
            commentRepository.deleteById(id);
            commentRepository.deleteReply(id);
        } else {
            commentRepository.deleteById(id);
        }
    }

    @Transactional
    @Override
    public void deleteAllCommunutyComment(Long no) {
        commentRepository.deleteAllCommunutyComment(no);
    }

    @Override
    public Long commnetCnt(Long no) {
        return commentRepository.commentCnt(no);
    }

}
