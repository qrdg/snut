package com.curation.snut.controller;

import com.curation.snut.entity.Member;
import com.curation.snut.repository.MemberRepository;
import com.curation.snut.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.curation.snut.dto.MemberDTO;

import java.util.Map;
import java.util.Optional;

@RestController
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map body) {
        log.info("body >>>>> " + body);

        String email = body.get("email").toString();
        String nickName = body.get("nickName").toString();

        Optional<Member> anEmail = memberRepository.findByEmail(email);
        Optional<Member> anNickName = memberRepository.findByEmail(nickName);

        if (anEmail.isPresent()) {
            return new ResponseEntity<>("이메일 중복", HttpStatus.OK);
        } else if (anNickName.isPresent()) {
            return new ResponseEntity<>("닉네임 중복", HttpStatus.OK);
        } else {
            memberService.register(body);
            return new ResponseEntity<>("등록 완료", HttpStatus.OK);
        }
    }

    @PostMapping("/memberModify")
    public ResponseEntity<?> modifyForm(MemberDTO memberDTO) {
        memberService.updateMemberDTO(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
