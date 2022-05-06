package com.curation.snut.repository;

import java.util.List;

import com.curation.snut.entity.Curation;
import com.curation.snut.entity.Member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class CurationRepositorytest {

    @Autowired
    CurationRepository curationRepository;

    @Test
    public void insertCuration() {
        Curation curation = Curation.builder()
                .curationTitle("place")
                .curationText("place abcd")
                .writer(Member.builder().email("r1").build())
                .build();
        curationRepository.save(curation);
    }

    @Test
    public void insertCuration2() {
        Curation curation = Curation.builder()
                .curationTitle("place2")
                .build();
        curationRepository.save(curation);
    }

    @Test
    public void sampleTest() {

        // IntStream.rangeClosed(1, 100).forEach(i -> {

        // Curation curation = Curation.builder()
        // .curationNo((long) i)
        // .curationText("text" + i)
        // .curationTitle("title..." + i)
        // .open(true)
        // .writer(Member.builder().email("email" + i).build())
        // .build();

        // log.info(curation);
        // });

        curationRepository.findAll();

        List<Curation> cuList = curationRepository.findAll();
        log.info("cuList............" + cuList);

    }
}
