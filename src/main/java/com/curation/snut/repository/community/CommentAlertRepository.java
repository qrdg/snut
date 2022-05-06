package com.curation.snut.repository.community;

import java.util.List;

import com.curation.snut.entity.community.CommentAlert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CommentAlertRepository extends JpaRepository<CommentAlert, Long> {

    @Query("select c from CommentAlert c where c.nickName =:nickName")
    List<CommentAlert> findMyAlert(String nickName);

}
