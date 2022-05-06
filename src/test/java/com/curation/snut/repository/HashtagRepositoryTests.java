package com.curation.snut.repository;

import com.curation.snut.dto.PageRequestDTO;
import com.curation.snut.dto.PageResultDTO;
import com.curation.snut.dto.SnutCollectionDTO;
import com.curation.snut.entity.Hashtag;
import com.curation.snut.entity.SnutCollection;
import com.curation.snut.service.HashTagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class HashtagRepositoryTests {

    @Autowired
    private HashtagRepository hashtagRepository;
    @Autowired
    private HashTagService hashTagService;
    @Autowired
    private SnutCollectionRepository snutCollectionRepository;

    @Test
    void searchHashtagTest() {
        List<Hashtag> list = hashtagRepository.findHashtagsByWord("테");
        list.stream().forEach(t -> System.out.println(t));
    }

    @Test
    void hashtagCountUpTest() {
        String word = "컬렉션1";
        hashTagService.upCountHashtag(word);
        List<Hashtag> list = hashtagRepository.findHashtagsByWord(word);
        list.stream().forEach(t -> System.out.println(t));
    }

    @Test
    void allHashtagUpCountTest() {
        List<Hashtag> list = hashtagRepository.findAll();
        list.forEach(tag -> {
            tag.upCount();
            hashtagRepository.save(tag);
        });
    }

    @Transactional
    @Test
    void getCollectionListByHashtagCountTest() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(15).build();
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("count").descending());
        Page<Hashtag> resultDTO = hashtagRepository.findAll(pageable);

        resultDTO.forEach(t -> System.out.println(t));

        List<Hashtag> list = resultDTO.stream().filter(i -> {
            SnutCollection col = snutCollectionRepository.findSnutCollectionByTag(i.getTag());
            return col != null;
        }).collect(Collectors.toList());
        list.forEach(i -> System.out.println(i));

        List<SnutCollection> colList = list.stream().map(tag -> {
            return snutCollectionRepository.findSnutCollectionByTag(tag.getTag());
        }).collect(Collectors.toList());

        colList.forEach(i -> System.out.println(i));

//        System.out.println("PREV: " + resultDTO.isPrev());
//        System.out.println("NEXT: " + resultDTO.isNext());
//        System.out.println("TOTAL: " + resultDTO.getTotalPage());
//
//        System.out.println("--------------------------------------------------------------------------");
//        resultDTO.getPageList().forEach(i -> System.out.println(i));
//        resultDTO.getDtoList().stream().forEach(j -> System.out.println(j));
    }

    @Transactional
    @Test
    void topCollectionByTagCountTest() {

    }
}