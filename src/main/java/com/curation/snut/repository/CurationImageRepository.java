package com.curation.snut.repository;

import com.curation.snut.entity.CurationImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CurationImageRepository extends JpaRepository<CurationImage, Long> {

    @Modifying
    @Query("delete from CurationImage cuImg where cuImg.curation.curationNo = :no")
    void deleteByCurationNo(Long no);

    @Query("select cuImg from CurationImage cuImg where cuImg.curation.curationNo = :no")
    List<CurationImage> findFileDataByCurationNo(Long no);


}
