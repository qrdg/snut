package com.curation.snut.repository;

import com.curation.snut.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    @Query("select t from Hashtag t where t.tag like concat('%', :word, '%') ")
    List<Hashtag> findHashtagsByWord(String word);

}
