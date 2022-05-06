package com.curation.snut.repository;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import com.curation.snut.entity.Curation;

import com.curation.snut.entity.CurationImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CurationRepository extends JpaRepository<Curation, Long> {

    // 성진
    @Query("select cu from Curation cu where cu.writer.email = :email")
    List<Curation> findCurationByEmail(String email);

    @Query("select co.curation from ColCuration co where co.snutCollection.collectionNo = :no")
    List findCurationsByCollectionNo(Long no);

    @Query("select cu from Curation cu where cu.curationNo = :no order by cu.modDate asc")
    Curation findCurationsByCurationNo(Long no);

    @Query("select ci from CurationImage ci where ci.curation.curationNo = :no order by ci.ciid asc")
    List<CurationImage> findCurationImageByCurationNo(Long no);

    @Query("select cu from Curation cu join cu.hashtag h where h.tag like concat('%', :word, '%')")
    List<Curation> findOnlyCurationsByWord(String word);

    @Query("select cu from Curation cu join cu.hashtag h where h.tag like concat('%', :word, '%') group by cu")
    List<Curation> findCurationByWord(String word);

    @Query("select count(cu) from Curation cu where cu.writer.email = :email")
    int getCountCurationByEmail(String email);

    @Modifying
    @Query("delete from Curation co where co.curationNo = :no")
    void deleteByCurationNo(Long no);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



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