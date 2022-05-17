package com.curation.snut.controller.community;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.curation.snut.dto.community.CommentDTO;
import com.curation.snut.dto.community.CommunityDTO;
import com.curation.snut.entity.community.CommentAlert;
import com.curation.snut.entity.community.CommuJoinTemp;
import com.curation.snut.entity.community.CommunityComment;
import com.curation.snut.repository.community.CommentRepository;
import com.curation.snut.security.util.JWTUtil;
import com.curation.snut.service.JWTService;
import com.curation.snut.service.community.CommentAlertService;
import com.curation.snut.service.community.CommentService;
import com.curation.snut.service.community.CommuJoinService;
import com.curation.snut.service.community.CommuJoinTempService;
import com.curation.snut.service.community.CommunityService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
public class ControllerTest {
    private final CommentService commentService;
    private final CommunityService communityService;
    private final JWTUtil jwtUtil;
    private final JWTService jwtService;
    private final CommentRepository commentRepository;
    private final CommuJoinTempService commuJoinTempService;
    private final CommuJoinService commuJoinService;
    private final CommentAlertService commentAlertService;

    @GetMapping(value = "/test/commuList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CommunityDTO>> testList(String searchTitle) {
        if (searchTitle != null) {
            List<CommunityDTO> searchCommunityList = communityService.searchTitle(searchTitle);
            return new ResponseEntity<>(searchCommunityList, HttpStatus.OK);
        } else {
            List<CommunityDTO> list = communityService.communityListWithCnt();
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/test/commuList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> testWrite(CommunityDTO communityDTO) {
        communityService.write(communityDTO);
        return new ResponseEntity<>("완료", HttpStatus.CREATED);
    }

    @GetMapping(value = "/test/commuList/delete")
    public ResponseEntity<String> testdelete(Long no) {
        communityService.delete(no);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/test/commentList")
    public ResponseEntity<Page<CommentDTO>> commentListTest(
            @PageableDefault(size = 2, sort = "cno", direction = Direction.ASC) final Pageable pageable,
            final Long no) {
        Page<CommentDTO> commentDTOList = commentService.commentList(pageable, no);
        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }

    @PostMapping(value = "/test/commentList")
    public ResponseEntity<?> commentWrite(CommentDTO commentDTO) {
        commentService.write(commentDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> commentDelete(Long id) {
        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "test/test")
    public ResponseEntity<?> testtest(HttpServletRequest request, @RequestBody Map body) {
        String authHeader = request.getHeader("token");
        Map user = null;

        try {
            user = jwtUtil.validateAndExtract(authHeader.substring(7));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String test = ((Map) ((List) user.get("authorities")).get(0)).get("authority").toString();
        // String email = user.get("authorities").toString();

        String test2 = body.get("aaa").toString();

        Map<String, String> printtest = new HashMap<>();
        printtest.put("auth", test);
        printtest.put("aaa", test2);

        return new ResponseEntity<>(printtest, HttpStatus.OK);
    }

    @GetMapping(value = "api/test/test2")
    public ResponseEntity<?> testtest2(@RequestHeader Map header) {
        String token = header.get("token").toString();
        Map userDetail = null;
        try {
            userDetail = jwtUtil.validateAndExtract(token.substring(7));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String info = userDetail.toString();
        return new ResponseEntity<>(userDetail, HttpStatus.OK);
    }

    @GetMapping(value = "test/test/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> testestse(CommentDTO commentDTO) {
        CommunityComment dto = commentRepository.findCno(commentDTO.getParentNo());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/test123") // 가입신청/내댓글알람 보는 페이지
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

}
