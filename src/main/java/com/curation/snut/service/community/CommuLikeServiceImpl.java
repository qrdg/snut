package com.curation.snut.service.community;

import com.curation.snut.dto.community.CommuLikeDTO;
import com.curation.snut.entity.community.CommunityLike;
import com.curation.snut.repository.community.CommuLikeRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommuLikeServiceImpl implements CommuLikeService {
    private final CommuLikeRepository commuLikeRepository;

    @Override
    public void reg(CommuLikeDTO commuLikeDTO) {
        CommunityLike communityLike = dtoToEntity(commuLikeDTO);
        commuLikeRepository.save(communityLike);
    }

}
