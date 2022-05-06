package com.curation.snut.service;

import java.util.List;
import java.util.Map;

public interface StoredDataService {

    void storeCurationByCurationNo(Long id, String email, String cuCo);

    Map getStoredDataByEmail(String email);
}
