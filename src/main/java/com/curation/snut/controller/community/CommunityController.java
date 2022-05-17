package com.curation.snut.controller.community;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.curation.snut.dto.community.CommuJoinDTO;
import com.curation.snut.dto.community.CommunityDTO;
import com.curation.snut.entity.community.CommentAlert;
import com.curation.snut.entity.community.CommuJoin;
import com.curation.snut.entity.community.CommuJoinTemp;
import com.curation.snut.service.JWTService;
import com.curation.snut.service.community.CommentAlertService;
import com.curation.snut.service.community.CommuJoinService;
import com.curation.snut.service.community.CommuJoinTempService;
import com.curation.snut.service.community.CommunityService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/community")
public class CommunityController {
    private final CommunityService communityService;
    private final CommuJoinTempService commuJoinTempService;
    private final CommuJoinService commuJoinService;
    private final CommentAlertService commentAlertService;
    private final JWTService jwtService;

    @GetMapping(value = "/commuList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CommunityDTO>> commuList(String searchTitle) {
        if (searchTitle != null) {
            List<CommunityDTO> searchCommunityList = communityService.searchTitle(searchTitle);
            return new ResponseEntity<>(searchCommunityList, HttpStatus.OK);
        } else {
            List<CommunityDTO> list = communityService.communityListWithCnt();
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/myCommuList") // 내가 가입한 커뮤 , 내가 만든 커뮤
    public ResponseEntity<?> mycommuList(@RequestHeader Map header) {
        Map userInfo = jwtService.code(header);
        String memberEmail = userInfo.get("email").toString();
        List<CommuJoin> list = commuJoinService.findJoinCommu(memberEmail);
        List<CommunityDTO> myCommuList = communityService.findMyCommu(memberEmail);

        Map send = new HashMap<>();

        send.put("joinCommunity", list);
        send.put("myCommunity", myCommuList);

        return new ResponseEntity<>(send, HttpStatus.OK);
    }

    @PostMapping(value = "/commuList", produces = MediaType.APPLICATION_JSON_VALUE) // 커뮤니티생성
    public ResponseEntity<String> commuReg(CommunityDTO communityDTO) {
        communityService.write(communityDTO);
        return new ResponseEntity<>("완료", HttpStatus.CREATED);
    }

    @PostMapping(value = "/commuList/delete") // 커뮤니티삭제
    public ResponseEntity<String> communityDelete(@RequestHeader Map header, @RequestBody Map body) {
        Map userInfo = jwtService.code(header);
        String memberEmail = userInfo.get("email").toString();

        String commuEmail = body.get("commuCreater").toString();
        Long commuNo = Long.valueOf(body.get("commuNo").toString());

        if (memberEmail.equals(commuEmail)) {
            communityService.delete(commuNo);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("권한없음", HttpStatus.OK);
        }

    }

    @PostMapping(value = "/commuJoinApply") // 커뮤가입신청
    public ResponseEntity<String> commuJoinApply(CommuJoinDTO commuJoinDTO) {
        String message = commuJoinTempService.joinApply(commuJoinDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping(value = "/commuMyPage") // 가입신청/내댓글알람 보는 페이지
    public ResponseEntity<?> commuMyPage(@RequestHeader Map header) {
        Map userInfo = jwtService.code(header);
        String memberEmail = userInfo.get("email").toString();
        String memberNickName = userInfo.get("nickName").toString();
        List<CommuJoinTemp> joinAlertList = commuJoinTempService.joinAlertList(memberEmail);
        List<CommentAlert> commentAlerts = commentAlertService.findMyAlert(memberNickName);

        Map send = new HashMap<>();

        send.put("joinAlertList", joinAlertList);
        send.put("commentAlerts", commentAlerts);

        return new ResponseEntity<>(send, HttpStatus.OK);
    }

    @PostMapping(value = "/commuJoinAccept") // 가입신청 수락
    public ResponseEntity<?> commuJoinAccept(CommuJoinDTO commuJoinDTO) {
        commuJoinService.joinAccept(commuJoinDTO);
        commuJoinTempService.joinAcceptAfterProcess(commuJoinDTO);
        return new ResponseEntity<>("수락 완료", HttpStatus.OK);
    }

    @PostMapping(value = "/commentAletDelete") // 알람확인버튼 클릭시 알람엔티티에 내용 삭제
    public ResponseEntity<?> commentAletDelete(Long id) {
        commentAlertService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/commuJoinList", produces = MediaType.APPLICATION_JSON_VALUE) // 내가 관리자인 커뮤니티에 가입한 유저 목록
    public ResponseEntity<List<CommuJoin>> commuJoinList(@RequestHeader Map header) {
        Map userInfo = jwtService.code(header);
        String memberEmail = userInfo.get("email").toString();
        List<CommuJoin> joinList = commuJoinService.joinList(memberEmail);
        return new ResponseEntity<>(joinList, HttpStatus.OK);
    }

    @PostMapping(value = "/commuJoinList/commuJoinDelete") // 가입한 유저 강퇴 (권한 삭제)
    public ResponseEntity<?> commuJoinDelete(CommuJoinDTO commuJoinDTO) {
        commuJoinService.joinDelete(commuJoinDTO);
        return new ResponseEntity<>("강퇴 완료", HttpStatus.OK);
    }

}
