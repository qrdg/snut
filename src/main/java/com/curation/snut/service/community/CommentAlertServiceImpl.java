package com.curation.snut.service.community;

import java.util.List;

import com.curation.snut.entity.community.CommentAlert;
import com.curation.snut.repository.community.CommentAlertRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentAlertServiceImpl implements CommentAlertService {

    private final CommentAlertRepository commentAlertRepository;

    @Override
    public List<CommentAlert> findMyAlert(String nickName) {
        return commentAlertRepository.findMyAlert(nickName);
    }

    @Override
    public void delete(Long id) {
        commentAlertRepository.deleteById(id);
    }

}
