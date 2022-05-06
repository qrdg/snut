package com.curation.snut.repository;

import com.curation.snut.dto.CurationDTO;
import com.curation.snut.entity.*;
import com.curation.snut.security.role.MemberRole;
import com.curation.snut.service.CurationService;
import com.curation.snut.service.SnutCollectionService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.core.parameters.P;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.IntStream;

@Log4j2
@SpringBootTest
class CurationRepositoryTests {

    @Autowired
    private CurationRepository curationRepository;
    @Autowired
    private SnutCollectionRepository collectionRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ColCurationRepository colCurationRepository;

    @Autowired
    private SnutCollectionRepository snutCollectionRepository;

    @Autowired
    private CurationService curationService;

    @Autowired
    private HashtagRepository hashtagRepository;
    @Autowired
    private SnutCollectionService snutCollectionService;
    @Autowired
    private MemberCurationLikeRepository memberCurationLikeRepository;

    @Test
    void makeDummies() {

        IntStream.rangeClosed(1, 100).forEach(i -> {

            Member member = Member.builder()
                    .email("email" + i + "@gmail.com")
                    .name("name..." + i)
                    .pw("1")
                    .nickName("nickName.." + i)
                    .gender("male")
                    .birth("1995-02-02")
                    .mobile("010-1234-5678")
                    .build();
            member.addMemberRole(MemberRole.USER);

            memberRepository.save(member);

        });

    }

    @Transactional
    @Commit
    @Test
    void makeCurationDummies() {

        IntStream.rangeClosed(1, 300).forEach(i -> {

            long random5 = (long) (Math.floor(Math.random() * 11)+1);
            long random1 = (long) (Math.floor(Math.random() * 300)+1);
            long random2 = (long) (Math.floor(Math.random() * 300)+1);
            long random3 = (long) (Math.floor(Math.random() * 300)+1);

            System.out.println("random5 >>> "+random5);

            Hashtag hashtag1 = Hashtag.builder().tag("hashtag"+random1).build();
            Hashtag hashtag2 = Hashtag.builder().tag("hashtag"+random2).build();
            Hashtag hashtag3 = Hashtag.builder().tag("hashtag"+random3).build();

            Set hashtagSet = new HashSet();
            hashtagSet.add(hashtag1);
            hashtagSet.add(hashtag2);
            hashtagSet.add(hashtag3);

            hashtagRepository.save(hashtag1);
            hashtagRepository.save(hashtag2);
            hashtagRepository.save(hashtag3);

            Curation curation = Curation.builder()
                    .curationTitle("Curation Title...." + i)
                    .curationText("Curation Text....." + i)
                    .writer(Member.builder().email("email" + random5 + "@gmail.com").build())
                    .hashtag(hashtagSet)
                    .open(true)
                    .build();

            curationRepository.save(curation);

            System.out.println(curation);
        });
    }

    @Transactional
    @Commit
    @Test
    void makeCollectionDummies() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            long random1 = (long) (Math.floor(Math.random() * 100) + 1);

            long random2 = (long) (Math.floor(Math.random() * 300)+1);
            long random3 = (long) (Math.floor(Math.random() * 300)+1);
            long random4 = (long) (Math.floor(Math.random() * 300)+1);

            Hashtag hashtag1 = Hashtag.builder().tag("hashtag"+random2).build();
            Hashtag hashtag2 = Hashtag.builder().tag("hashtag"+random3).build();
            Hashtag hashtag3 = Hashtag.builder().tag("hashtag"+random4).build();

            Set hashtagSet = new HashSet();
            hashtagSet.add(hashtag1);
            hashtagSet.add(hashtag2);
            hashtagSet.add(hashtag3);

            hashtagRepository.save(hashtag1);
            hashtagRepository.save(hashtag2);
            hashtagRepository.save(hashtag3);


                SnutCollection collection = SnutCollection.builder()
                        .collectionTitle("Collection Title...." + i)
                        .collectionText("Collection Text..." + i)
                        .writer(Member.builder().email("email" + random1 + "@gmail.com").build())
                        .hashtag(hashtagSet)
                        .build();

                collectionRepository.save(collection);

            System.out.println(collection);
            System.out.println(hashtagSet);
        });
    }

    @Test
    void NvsNDummiesTest() {
        IntStream.rangeClosed(1, 300).forEach(i -> {

            Long collectionNo = (long)(Math.random()*100) + 1;

            Long mid = (long)(Math.random()*300) + 1;

            ColCuration colCuration = ColCuration.builder()
                    .snutCollection(SnutCollection.builder().collectionNo(collectionNo).build())
                    .curation(Curation.builder().curationNo(mid).build())
                    .build();

            colCurationRepository.save(colCuration);

        });
    }

    @Test
    void getCollectionTest() {

        Curation list = curationRepository.findCurationsByCurationNo(332L);
        List<CurationImage> ciList = curationRepository.findCurationImageByCurationNo(332L);

        System.out.println("list.size() >>> " + list);

        for(CurationImage arr : ciList) {
            System.out.println(arr);
        }
    }

    @Transactional
    @Test
    void curationSearchTest() {

        List<Curation> list = curationRepository.findCurationByWord("테");
        System.out.println(list.size());

        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    @Transactional
    @Test
    void curationTest() {
        List<CurationDTO> list = curationService.getCurationsByWord("테");
        System.out.println(list.size());
        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    @Test
    void collectionTest() {
        List list = snutCollectionRepository.findCurationByWord("가");

        System.out.println(list.size());
//        int bb = 0;
//        for(Object a : list) {
//            SnutCollection b = (SnutCollection) a;
//            System.out.println(b.getCollectionNo());
//            System.out.println(b.getHashtag());
//            bb++;
//        }
//        System.out.println("bb >> " + bb);
    }

    @Transactional
    @Test
    void getCurationsbyCollectionNoTest() {
        List<CurationDTO> cuList = curationService.getCurationsByCollectionNo(204L);

        for (int i = 0; i < cuList.size(); i++) {
            System.out.println(cuList.get(i));
        }
    }

    @Transactional
    @Test
    void getCurationsByEmailTest() {
        List<CurationDTO> cuDTO = curationService.getCurationByEmail("fo");

        for (int i = 0; i < cuDTO.size(); i++) {
            System.out.println(cuDTO.get(i));
        }
    }

    @Transactional
    @Test
    void findLikeTest() {
        Long a = memberCurationLikeRepository.findCurationByEmailAndCurationNo(2L, "fo");
        System.out.println(a);
    }
}