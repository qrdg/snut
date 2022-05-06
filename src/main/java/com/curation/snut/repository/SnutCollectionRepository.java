package com.curation.snut.repository;

import com.curation.snut.entity.SnutCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SnutCollectionRepository extends JpaRepository<SnutCollection, Long> {

    @Query("select sc from SnutCollection sc where sc.writer.email = :email")
    List<SnutCollection> findSnutCollectionByEamil(String email);

    @Query("select distinct sc from SnutCollection sc join sc.hashtag h where h.tag like concat('%', :word, '%')")
    List findCurationByWord(@Param("word") String word);

//    @Query("select distinct sc from SnutCollection sc join sc.hashtag h order by h.count desc")
//    Page<SnutCollection> findSnutCollectionByHashTagCount(Pageable pageable);
    @Query(value = "SELECT * FROM snut_collection s, snut_collection_hashtag sh " +
            "JOIN hashtag h " +
            "ON h.hid = sh.hashtag_hid " +
            "WHERE s.collection_no = sh.snut_collection_collection_no " +
            "GROUP BY s.collection_no ORDER BY h.count desc", nativeQuery = true)
    Page<SnutCollection> findSnutCollectionByHashTagCount(Pageable pageable);

    @Query("select sc from SnutCollection sc join sc.hashtag h where h.tag = :word")
    SnutCollection findSnutCollectionByTag(String word);

}
