package com.curation.snut.controller;

import com.curation.snut.dto.CurationDTO;
import com.curation.snut.dto.PageRequestDTO;
import com.curation.snut.dto.SnutCollectionDTO;
import com.curation.snut.service.CurationService;
import com.curation.snut.service.HashTagService;
import com.curation.snut.service.SnutCollectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
public class HomeController {

    private final CurationService curationService;
    private final SnutCollectionService snutCollectionService;
    private final HashTagService hashTagService;

        // 컬렉션 번호 기준으로 큐레이션 데이터 모두 가져오기 - UserCollection.vue에서 사용
        @GetMapping("/ucol")
        public ResponseEntity getCurationDataInCollection(@RequestParam("collectionNo") Long collectionNo) {
            log.info("request collectionNo >>>>>>>>>>> " + collectionNo);
            List<CurationDTO> curationDTOList = curationService.getCurationsByCollectionNo(collectionNo);

            return new ResponseEntity(curationDTOList, HttpStatus.OK);
        }

        // 단어 검색 횟수 기준으로 상위 5개씩 페이징 처리된 컬렉션 가져오기
        @GetMapping(value = "/main/hot")
        public ResponseEntity getCollectionPoPularList(PageRequestDTO pageRequestDTO) {
            log.info("list............................." + pageRequestDTO);

            return new ResponseEntity(snutCollectionService.getPopularCollection(pageRequestDTO), HttpStatus.OK);
        }

    // 검색단어 기준으로 컬렉션 및 큐레이션 데이터 가져오기 - SearchCollection.vue에서 사용 (검색 페이지)
    @GetMapping(value = "/main", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity dataSetBySearchWord(@RequestParam Map searchWord) {
        log.info("dataSetBySearchWord............");
        log.info("searchWord >>>" + searchWord);
        String word = (String) searchWord.get("word");
        log.info("searchWord >>>" + word);

        List<CurationDTO> curations = curationService.getCurationsByWord(word);
        List<SnutCollectionDTO> collections = snutCollectionService.getCollectionsByWord(word);
        log.info("이 큐레이션은 비었나요? " + curations.isEmpty());
        log.info("이 컬렉션은 비었나요? " + collections.isEmpty());

        hashTagService.upCountHashtag(word);

        Map a = new HashMap<>();
        a.put("Collection", collections);
        a.put("Curation", curations);

        return new ResponseEntity(a, HttpStatus.OK);
//        return new ResponseEntity(HttpStatus.OK);
    }
}
