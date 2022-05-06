package com.curation.snut.service.community;

import java.util.List;

import com.curation.snut.entity.community.CommentAlert;

public interface CommentAlertService {
    List<CommentAlert> findMyAlert(String nickName);

    void delete(Long id);
}
