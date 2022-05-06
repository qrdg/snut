package com.curation.snut.repository;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import com.curation.snut.entity.Member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberRepositoryTests {
    @Autowired
    MemberRepository memberRepository;
  
    @Autowired
    CurationRepository curationRespository;
  
    @Test
    public void insertMembers() {
      IntStream.rangeClosed(1, 100).forEach(new IntConsumer() {
        @Override
        public void accept(int i) {
          Member member = Member.builder()
          .email("r" + i )
              .name("i")
              .pw("1")
              .mobile("i"+"i")
              .birth("i")
              .gender("i")
              .nickName("member" + i)
              .build();
          memberRepository.save(member);
        }
      });
    }
  

  

    
}
