package com.curation.snut.service.community;

import java.util.List;

import com.curation.snut.dto.community.CommuJoinDTO;
import com.curation.snut.entity.community.CommuJoin;

public interface CommuJoinService {
    void joinAccept(CommuJoinDTO commuJoinDTO);

    void joinDelete(CommuJoinDTO commuJoinDTO);

    List<CommuJoin> findJoinCommu(String email);

    List<CommuJoin> joinList(String email);
}
