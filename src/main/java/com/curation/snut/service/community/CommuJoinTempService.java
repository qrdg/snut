package com.curation.snut.service.community;

import java.util.List;
import java.util.Map;

import com.curation.snut.dto.community.CommuJoinDTO;
import com.curation.snut.entity.community.CommuJoinTemp;

public interface CommuJoinTempService {

    String joinApply(CommuJoinDTO commuJoinDTO);

    void joinAcceptAfterProcess(CommuJoinDTO commuJoinDTO);

    List<CommuJoinTemp> joinAlertList(String memberEmail);

}
