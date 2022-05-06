package com.curation.snut.controller.community;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.curation.snut.dto.community.CommentDTO;
import com.curation.snut.entity.community.CommuJoin;
import com.curation.snut.entity.community.Community;
import com.curation.snut.entity.community.CommunityComment;
import com.curation.snut.repository.community.CommentRepository;
import com.curation.snut.repository.community.CommuJoinRepository;
import com.curation.snut.repository.community.CommunityRepository;
import com.curation.snut.service.JWTService;
import com.curation.snut.service.community.CommentService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class CommentController {
    private final CommentService commentService;
    private final CommuJoinRepository commuJoinRepository;
    private final CommunityRepository communityRepository;
    private final CommentRepository commentRepository;
    private final JWTService jwtService;

    @GetMapping(value = "/commentList")
    public ResponseEntity<Map<String, Page<CommentDTO>>> commentListTest(
            @PageableDefault(page = 0, size = 10) final Pageable pageable, final Long no) {
        Page<CommentDTO> commentDTOList = commentService.commentList(pageable, no);
        Page<CommentDTO> commentDTOList2 = commentService.ancommentList(pageable, no);

        System.out.println("commentDTOList >>>>>>> " + commentDTOList);
        System.out.println("commentDTOList2 >>>>>>> " + commentDTOList2);

        Map<String, Page<CommentDTO>> map = new HashMap<>();
        map.put("import", commentDTOList2);
        map.put("nomal", commentDTOList);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping(value = "/commentList", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> commentWrite(@RequestBody CommentDTO commentDTO) {

        String writer = commentDTO.getWriter().getEmail();
        Long commuNo = commentDTO.getCommunityName().getNo();
        Optional<CommuJoin> findWriteAuthority = commuJoinRepository.findWriteAuthority(commuNo, writer);

        Optional<Community> findCreater = communityRepository.findByCreater(writer, commuNo);
        System.out.println("writer >>>>>>>>>>> " + writer);
        System.out.println("commuNo >>>>>>>>>>> " + commuNo);
        System.out.println("findWriteAuthority >>>>>>>>>>> " + findWriteAuthority);
        System.out.println("findCreater >>>>>>>>>>> " + findCreater);

        boolean check = commentDTO.isAnnouncement();
        if (!check) {
            if (findWriteAuthority.isPresent()) {
                commentService.write(commentDTO);
                return new ResponseEntity<>("등록", HttpStatus.OK);
            } else if (findCreater.isPresent()) {
                commentService.write(commentDTO);
                return new ResponseEntity<>("등록", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("권한 없음", HttpStatus.OK);
            }
        } else {
            if (findCreater.isPresent()) {
                commentService.write(commentDTO);
                return new ResponseEntity<>("등록", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("권한 없음", HttpStatus.OK);
            }
        }
        // return new ResponseEntity<>("에라이", HttpStatus.OK);
    }

    @PostMapping(value = "/comment/mod", consumes = MediaType.ALL_VALUE)
    public ResponseEntity commentModify(@RequestBody Map obj) {
        Long num = Long.valueOf(String.valueOf(obj.get("cno")));
        String content = (String) obj.get("content");
        Optional<CommunityComment> com = commentRepository.findById(num);
        System.out.println("com >>>>>>>>>>>>>>>> " + obj);
        com.get().setText(content);
        commentRepository.save(com.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/comment/delete", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> commentDelete(@RequestHeader Map header, @RequestBody Map body) {
        Map userInfo = jwtService.code(header);
        Map userSub = (Map) userInfo.get("sub");
        String memberEmail = userSub.get("email").toString();

        System.out.println("body >>>>>>>>>> " + body);
        Long cno = Long.valueOf(String.valueOf(body.get("cno")));
        String commnetEmail = body.get("commentEmail").toString();

        System.out.println("userInfo  >>>>>> " + userInfo);
        System.out.println("memberEmail  >>>>>> " + memberEmail);
        System.out.println("cno  >>>>>> " + cno);
        System.out.println("commnetEmail  >>>>>> " + commnetEmail);
        if (memberEmail.equals(commnetEmail)) {
            commentService.delete(cno);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("권한이 없습니다", HttpStatus.OK);
        }
    }
}
