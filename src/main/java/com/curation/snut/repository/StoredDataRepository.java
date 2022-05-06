package com.curation.snut.repository;

import com.curation.snut.entity.StoredData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoredDataRepository extends JpaRepository<StoredData, Long> {

    @Query("select sd.sid from StoredData sd where sd.cuCoId = :id and sd.email = :email and sd.cuCo = :cuCo")
    Long findExistByCuCoAndEmail(Long id, String email, String cuCo);

    @Query("select sd.cuCoId, sd.cuCo from StoredData sd where sd.email = :email")
    List<Object[]> getStoredDataByEmail(String email);

}
