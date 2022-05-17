package com.curation.snut.repository.curation;

import java.util.List;

import javax.transaction.Transactional;

import com.curation.snut.entity.curation.Curation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CurationRepository extends JpaRepository<Curation, Long> {

    @Query("select c from Curation c order by curationNo")
    Page<Curation> list(Pageable pageable, Long curationNo);

    @Modifying
    @Transactional
    @Query("delete from Curation c where c.curationNo = :id")
    void deleteCuration(Long id);

    // 쿼리수정
    // @Query("select c, curationNo from Curation c left outer join c.title
    // like%:searchTitle%")
    List<Object[]> findByCurationTitleContaining(String searchTitle);

    Object getCurationByCurationNo(@Param("curationNo") Long curationNo);
}