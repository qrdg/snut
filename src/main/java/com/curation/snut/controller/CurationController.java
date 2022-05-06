package com.curation.snut.controller;

import com.curation.snut.dto.CurationDTO;
import com.curation.snut.dto.SnutCollectionDTO;
import com.curation.snut.entity.Curation;
import com.curation.snut.entity.Member;
import com.curation.snut.entity.SnutCollection;
import com.curation.snut.repository.*;
import com.curation.snut.service.CurationService;
import com.curation.snut.service.JWTService;
import com.curation.snut.service.SnutCollectionService;
import com.curation.snut.service.memberlike.MemberCollectionLikeService;
import com.curation.snut.service.memberlike.MemberCurationLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class CurationController {

    private final CurationRepository curationRepository;
    private final SnutCollectionRepository snutCollectionRepository;
    private final CurationService curationService;
    private final SnutCollectionService snutCollectionService;
    private final MemberCollectionLikeService memberCollectionLikeService;
    private final MemberCurationLikeService memberCurationLikeService;
    private final MemberCurationLikeRepository memberCurationLikeRepository;
    private final MemberCollectionLikeRepository memberCollectionLikeRepository;
    private final JWTService jwtService;
    private final MemberRepository memberRepository;

    // 성진
    // 이메일에 대한 큐레이션 갯수 산정하기 - MyCollection.vue에서 사용
    @GetMapping("/level")
    public ResponseEntity getMemberLevel(@RequestHeader Map header) {
        log.info("getMemberLevel...............................");
        Map userInfo = jwtService.code(header);
        Map userSub = (Map) userInfo.get("sub");
        String email = userSub.get("email").toString();

        Optional<Member> member = memberRepository.findByEmail(email);
        member.get().confirmMemberLevel(curationRepository.getCountCurationByEmail(email));
        memberRepository.save(member.get());

        int level = member.get().getMemberLevel();
        return new ResponseEntity(level, HttpStatus.OK);
    }

    // 이메일로 큐레이션 리스트 모두 가져오기 - Make Collection.vue에서 사용 (컬렉션 만들기 페이지)
    @GetMapping(value = "/mcol/mc/em")
    public ResponseEntity getListByEmail(@RequestParam("email") String email) {

        log.info("getListByEmail >>>>>>>>>>> " + email);
        List<CurationDTO> culist = curationService.getCurationByEmail(email);
        log.info("getListByEmail >>>>>>>>>>> " + culist);

        return new ResponseEntity(culist, HttpStatus.OK);
    }

    // 이메일 기준으로 모든 큐레이션, 콜렉션 가져오기 - My Note.vue에서 사용 (나만의 기록 페이지)
    @GetMapping("/mcol/mn")
    public ResponseEntity getAllListByEmail(@RequestParam("email") String email) {

        List<CurationDTO> culist = curationService.getCurationByEmail(email);
        List<SnutCollectionDTO> colList = snutCollectionService.getCollectionsByEmail(email);
        HashMap sendMap = new HashMap();
        sendMap.put("Curation", culist);
        sendMap.put("Collection", colList);

        return new ResponseEntity(sendMap, HttpStatus.OK);
    }

    // 큐레이션 ID값 기준으로 큐레이션 가져오기 - MakeColSave.vue에서 사용 (컬렉션 만들기 페이지)
    @RequestMapping(value = "/mcol/store", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CurationDTO cuListByCurationNo(@RequestParam Map body) {
        log.info("cuListByCurationNo............");
        log.info("no >>>" + body.get("curationId"));
        Long a = Long.parseLong((String) body.get("curationId"));
        return curationService.getCurationsByCurationNo(a);
    }


    // 큐레이션 등록하기 - MakeNote.vue에서 사용 (큐레이션 등록하기)
    @PostMapping(value = "/mcol/note/makenote/picture", consumes = MediaType.ALL_VALUE)
    public ResponseEntity curationRegister(@RequestBody CurationDTO curationDTO) {
        log.info("curationDTO >>>> " + curationDTO);
        Long curationNo = curationService.register(curationDTO);

        return new ResponseEntity(curationNo, HttpStatus.OK);
    }

    // 컬렉션 등록하기 - MakeCollection.vue에서 사용 (컬렉션 등록하기)
    @PostMapping(value = "/mcol/mc", consumes = MediaType.ALL_VALUE)
    public ResponseEntity collectionRegister(@RequestBody SnutCollectionDTO snutCollectionDTO) {
        log.info("collectionDTO >>>>>>>> " + snutCollectionDTO);
        Long collectionNo = snutCollectionService.snutCollectionRegister(snutCollectionDTO);

        return new ResponseEntity(collectionNo, HttpStatus.OK);
    }

    @DeleteMapping("/cu/del")
    public ResponseEntity curationDelete(@RequestParam Long no) {
        Long num = Long.valueOf(String.valueOf(no));
        System.out.println("no.........." + num);
        curationService.deleteCurationById(num);

        return new ResponseEntity("삭제 성공!", HttpStatus.OK);
    }

    // 좋아요 구간
    @GetMapping("/find/good")
    public Long findExistGood(@RequestParam Map body) {

        Long num = Long.valueOf(String.valueOf(body.get("no")));
        String email = (String) body.get("email");
        String cuCo = (String) body.get("cuCo");

        System.out.println("no......qwdq...." + num);
        System.out.println("email..qwd......" + email);
        log.info("cuCo....dqwdqwdqwd...." + cuCo);
        if (cuCo == "Collection") {
            Long result = memberCollectionLikeRepository.findCollectionByEmailAndCollectionNo(num, email);
            System.out.println("col result.........................." + result);
            return result;
        } else if(cuCo == "Curation") {
            Long result = memberCurationLikeRepository.findCurationByEmailAndCurationNo(num, email);
            log.info("cu result.........................." + result);
            return result;
        }
//        System.out.println("result.........................." + result);
        return null;
    }

    // 컬렉션 좋아요 등록
    @PostMapping("/col/likes")
    public String collectionlikes(@RequestBody Map data) {
        log.info("likes >>>>>> " + data);
        Long no = Long.valueOf(String.valueOf(data.get("no")));
        String email = (String) data.get("email");

        if (memberCollectionLikeRepository.findCollectionByEmailAndCollectionNo(no, email) == null) {
            memberCollectionLikeService.likes(no, email);
            Optional<SnutCollection> col = snutCollectionRepository.findById(no);
            col.get().setLike(true);
            snutCollectionRepository.save(col.get());

            return "좋아요 완료";
        }
        return "중복";
    }
    // 컬렉션 좋아요 해제
    @DeleteMapping("/col/unlikes")
    public String collectionUnlikes(@RequestParam Map data) {
        log.info("likes >>>>>> " + data);
        Long no = Long.valueOf(String.valueOf(data.get("no")));
        String email = (String) data.get("email");
        if (memberCollectionLikeRepository.findCollectionByEmailAndCollectionNo(no, email) != null) {
            memberCollectionLikeService.unlikes(no, email);
            Optional<SnutCollection> col = snutCollectionRepository.findById(no);
            col.get().setLike(false);
            snutCollectionRepository.save(col.get());
            return "좋아요 해제";
        }
        return "좋아요 해제할 데이터 없음";
    }
    // 큐레이션 좋아요 등록
    @PostMapping("/cu/likes")
    public String curationlikes(@RequestBody Map data) {
        log.info("likes >>>>>> " + data);
        Long no = Long.valueOf(String.valueOf(data.get("no")));
        String email = (String) data.get("email");
        if (memberCurationLikeRepository.findCurationByEmailAndCurationNo(no, email) == null) {
            System.out.println("???? >>>>>>> " + memberCurationLikeRepository.findCurationByEmailAndCurationNo(no, email) == null);
            memberCurationLikeService.likes(no, email);
            Optional<Curation> cu = curationRepository.findById(no);
            cu.get().setLike(true);
            curationRepository.save(cu.get());
            return "좋아요 완료";
        }
        return "중복";
    }
    // 큐레이션 좋아요 해제
    @DeleteMapping("/cu/unlikes")
    public String curationUnlikes(@RequestParam Map data) {
        log.info("likes >>>>>> " + data);
        Long no = Long.valueOf(String.valueOf(data.get("no")));
        String email = (String) data.get("email");
        if (memberCurationLikeRepository.findCurationByEmailAndCurationNo(no, email) != null) {
            memberCurationLikeService.unlikes(no, email);
            Optional<Curation> cu = curationRepository.findById(no);
            cu.get().setLike(false);
            curationRepository.save(cu.get());
            return "좋아요 해제 완료";
        }
        return "좋아요 해제할 데이터 없음";
    }

}
