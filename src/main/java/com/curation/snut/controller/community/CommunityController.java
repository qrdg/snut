package com.curation.snut.controller.community;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.curation.snut.dto.PageRequestDTO;
import com.curation.snut.dto.PageResultDTO;
import com.curation.snut.dto.community.CommuJoinDTO;
import com.curation.snut.dto.community.CommunityDTO;
import com.curation.snut.entity.community.CommentAlert;
import com.curation.snut.entity.community.CommuJoin;
import com.curation.snut.entity.community.CommuJoinTemp;
import com.curation.snut.entity.community.Community;
import com.curation.snut.repository.community.CommuJoinTempRepository;
import com.curation.snut.service.JWTService;
import com.curation.snut.service.community.CommentAlertService;
import com.curation.snut.service.community.CommuJoinService;
import com.curation.snut.service.community.CommuJoinTempService;
import com.curation.snut.service.community.CommunityService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class CommunityController {
    private final CommunityService communityService;
    private final CommuJoinTempService commuJoinTempService;
    private final CommuJoinService commuJoinService;
    private final CommentAlertService commentAlertService;
    private final CommuJoinTempRepository commuJoinTempRepository;
    private final JWTService jwtService;

    @GetMapping(value = "/commuList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResultDTO> commuList(PageRequestDTO pageRequestDTO, String searchTitle) {

        if (searchTitle == null) {
            PageResultDTO list = communityService.communityListWithCnt(pageRequestDTO);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else if (searchTitle != "all") {
            PageResultDTO searchCommunityList = communityService.searchTitle(pageRequestDTO, searchTitle);
            return new ResponseEntity<>(searchCommunityList, HttpStatus.OK);
        } else {
            return null;
        }
    }

    @GetMapping("/com/in")
    public ResponseEntity getCommunityData(@RequestParam("no") Long no) {
        CommunityDTO dto = communityService.getCommunityData(no);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @PostMapping(value = "/com/in/mod", consumes = MediaType.ALL_VALUE)
    public ResponseEntity modifyCommunityContent(@RequestBody Map data) {
        Long num = Long.valueOf(String.valueOf(data.get("no")));
        String content = (String) data.get("content");
        communityService.modifyCommunityContent(num, content);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/myCommuList") // 내가 가입한 커뮤 , 내가 만든 커뮤
    public ResponseEntity<?> mycommuList(@RequestHeader Map header) {

        Map userInfo = jwtService.code(header);
        System.out.println("userInfo.............." + userInfo);
        Map sub = (Map) userInfo.get("sub");
        String memberEmail = sub.get("email").toString();
        List<CommuJoin> list = commuJoinService.findJoinCommu(memberEmail);
        List<CommunityDTO> myCommuList = communityService.findMyCommu(memberEmail);

        Map send = new HashMap<>();

        send.put("joinCommunity", list);
        send.put("myCommunity", myCommuList);

        return new ResponseEntity<>(send, HttpStatus.OK);
//        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/commuList", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) // 커뮤니티생성
    public ResponseEntity<String> commuReg(@RequestBody CommunityDTO communityDTO) {
        communityService.write(communityDTO);
        return new ResponseEntity<>("완료", HttpStatus.CREATED);
    }

    @PostMapping(value = "/commuList/delete") // 커뮤니티삭제
    public ResponseEntity<String> communityDelete(@RequestHeader Map header, @RequestBody Map body) {
        Map userInfo = jwtService.code(header);
        Map userSub = (Map) userInfo.get("sub");

        String memberEmail = userSub.get("email").toString();
        String commuEmail = body.get("commuCreater").toString();
        Long commuNo = Long.valueOf(body.get("commuNo").toString());
//
        if (memberEmail.equals(commuEmail)) {
            communityService.delete(commuNo);
            return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("권한없음", HttpStatus.OK);
        }
    }

    @PostMapping(value = "/commuJoinApply") // 커뮤가입신청
    public ResponseEntity<String> commuJoinApply(@RequestBody CommuJoinDTO commuJoinDTO) {
        System.out.println("commuJoinDTO................" + commuJoinDTO);
        String message = commuJoinTempService.joinApply(commuJoinDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping(value = "/commuJoinReject")
    public ResponseEntity<String> commuJoinReject(@RequestParam("num") Long num) {
        Long no = commuJoinTempRepository.findRejectCommunityNo(num);
        if(no != null) {
            commuJoinTempRepository.deleteByCommunityNo(no);
            return new ResponseEntity<>("거절 처리 되었습니다.", HttpStatus.OK);
        }
        return new ResponseEntity<>("잘못된 정보입니다.", HttpStatus.OK);
    }

    @GetMapping(value = "/commuMyPage") // 가입신청/내댓글알람 보는 페이지
    public ResponseEntity<?> commuMyPage(@RequestHeader Map header) {
        Map userInfo = jwtService.code(header);

        Map userSub = (Map) userInfo.get("sub");
        String memberEmail = userSub.get("email").toString();
        String memberNickName = userSub.get("nickName").toString();
        List<CommuJoinTemp> joinAlertList = commuJoinTempService.joinAlertList(memberEmail);
        List<CommentAlert> commentAlerts = commentAlertService.findMyAlert(memberNickName);

        Map send = new HashMap<>();

        send.put("joinAlertList", joinAlertList);
        send.put("commentAlerts", commentAlerts);

        return new ResponseEntity<>(send, HttpStatus.OK);
    }

    @PostMapping(value = "/commuJoinAccept") // 가입신청 수락
    public ResponseEntity<?> commuJoinAccept(@RequestBody CommuJoinDTO commuJoinDTO) {
        System.out.println("CommuJoinDTO................." + commuJoinDTO);
        commuJoinService.joinAccept(commuJoinDTO);
        commuJoinTempService.joinAcceptAfterProcess(commuJoinDTO);
        return new ResponseEntity<>("수락 완료", HttpStatus.OK);
    }

    @PostMapping(value = "/commentAletDelete") // 알람확인버튼 클릭시 알람엔티티에 내용 삭제
    public ResponseEntity<?> commentAletDelete(@RequestBody Map obj) {
        commentAlertService.delete(Long.valueOf(String.valueOf(obj.get("id"))));
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
