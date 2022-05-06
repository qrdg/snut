package com.curation.snut.repository;

import com.curation.snut.dto.PageRequestDTO;
import com.curation.snut.dto.PageResultDTO;
import com.curation.snut.dto.SnutCollectionDTO;
import com.curation.snut.entity.Curation;
import com.curation.snut.entity.Hashtag;
import com.curation.snut.entity.Member;
import com.curation.snut.entity.SnutCollection;
import com.curation.snut.service.SnutCollectionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SnutCollectionRepositoryTests {

    @Autowired
    private SnutCollectionService snutCollectionService;
    @Autowired
    private SnutCollectionRepository snutCollectionRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CurationRepository curationRepository;
    @Autowired
    private HashtagRepository hashtagRepository;

    @Transactional
    @Commit
    @Test
    void collectionRegisterTest() {
        int num = 1;

        Set<Hashtag> tagSet = new HashSet<>();
        List<Curation> cuList = new ArrayList<>();
        List<Long> cuIdList = new ArrayList<>();

        for(int i = 0; i < 3; i++) {
            Hashtag hashtag = Hashtag.builder().tag("sampleTag" + i).build();
            hashtagRepository.save(hashtag);
            tagSet.add(hashtag);

//            Optional<Curation> cu = curationRepository.findById((long)332 + i);
//            cuList.add(cu.get());
            cuIdList.add((long) 335 + i);
        }

        SnutCollectionDTO colDTO = SnutCollectionDTO.builder()
                .collectionTitle("Collection Title" + num)
                .collectionText("Collection Text" + num)
                .hashtag(tagSet)
                .email("email10@gmail.com")
                .curations(cuIdList)
                .build();

        Long result = snutCollectionService.snutCollectionRegister(colDTO);

        System.out.println(result);
    }

    @Transactional
    @Test
    void getCollectionsBySearchWord() {
        List<SnutCollectionDTO> list = snutCollectionService.getCollectionsByWord("가");
        System.out.println(list);
        System.out.println(list.size());
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
//        System.out.println("aowirjgoiqejwiorgj");
    }

    @Transactional
    @Test
    void getCurationsByCollectionNo() {
        List<Curation> list = curationRepository.findCurationsByCollectionNo(220L);
        System.out.println(list);
        System.out.println(list.size());
    }

    @Transactional
    @Test
    void getPopularCollectionTest() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(5).build();
//        Pageable pageable = pageRequestDTO.getPageable(Sort.by("collectionNo").descending());
        PageResultDTO<SnutCollectionDTO, SnutCollection> resultDTO = snutCollectionService.getPopularCollection(pageRequestDTO);

//        for (int i = 0; i < list.getSize(); i++) {
//            System.out.println(list);
//        }

        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("--------------------------------------------------------------------------");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
        resultDTO.getDtoList().stream().forEach(j -> System.out.println(j));

    }

    @Transactional
    @Test
    void getPageListCollectionTest() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(5).build();
        PageResultDTO<SnutCollectionDTO, SnutCollection> resultDTO = snutCollectionService.getList(pageRequestDTO);

//        for(SnutCollectionDTO snutCollectionDTO : resultDTO.getDtoList()) {
//            System.out.println(snutCollectionDTO);
//        }

        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("--------------------------------------------------------------------------");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
        resultDTO.getDtoList().stream().forEach(j -> System.out.println(j));
    }

    @Transactional
    @Test
    void getPopularCollectionListByHashtagCountTest() {
        PageRequestDTO requestDTO = PageRequestDTO.builder().page(1).size(20).build();
//        Pageable pageable = requestDTO.getPageable(Sort.by("count").descending());
        Pageable pageable = PageRequest.of(1, 20);
        Page<SnutCollection> result = snutCollectionRepository.findSnutCollectionByHashTagCount(pageable);
        result.stream().forEach(i -> System.out.println("아닛 >> " + i.getHashtag()));
    }

    @Transactional
    @Test
    void hashTagColTest() {
        PageRequestDTO requestDTO = PageRequestDTO.builder().page(1).size(20).build();
        PageResultDTO resultDTO = snutCollectionService.getPopularCollection(requestDTO);
        resultDTO.getDtoList().forEach(i -> System.out.println(i));
    }
}