package com.curation.snut.repository;

import com.curation.snut.entity.ColCuration;
import com.curation.snut.entity.Curation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ColCurationRepository extends JpaRepository<ColCuration, Long> {
    @Query("select colc.curation.curationNo from ColCuration colc where colc.snutCollection.collectionNo = :no")
    List<Long> getCurationNoListByCollectionNo(Long no);

}
