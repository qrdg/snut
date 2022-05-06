package com.curation.snut.controller;

import com.curation.snut.repository.StoredDataRepository;
import com.curation.snut.service.StoredDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Log4j2
public class StoredDataController {

    private final StoredDataRepository storedDataRepository;
    private final StoredDataService storedDataService;

    @PostMapping("/store")
    public ResponseEntity storeData(@RequestBody Map body) {

        Long no = Long.valueOf(String.valueOf(body.get("no")));
        String cuCo = (String) body.get("cuCo");
        String email = (String) body.get("email");

        Long inspectNo = storedDataRepository.findExistByCuCoAndEmail(no, email, cuCo);
        log.info("inspectNo.............." + inspectNo);
        if(inspectNo == null) {
            storedDataService.storeCurationByCurationNo(no, email, cuCo);
            return new ResponseEntity("등록 완료", HttpStatus.OK);
        } else {
            storedDataRepository.deleteById(inspectNo);
            return new ResponseEntity("삭제", HttpStatus.OK);
        }
    }

    @GetMapping("/store")
    public ResponseEntity getStoreData(@RequestParam("email") String email) {

        System.out.println(email);
        Map result = storedDataService.getStoredDataByEmail(email);

        return new ResponseEntity(result, HttpStatus.OK);
    }

}
