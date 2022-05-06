package com.curation.snut.service;

import com.curation.snut.dto.CurationDTO;
import com.curation.snut.dto.SnutCollectionDTO;
import com.curation.snut.entity.Curation;
import com.curation.snut.entity.CurationImage;
import com.curation.snut.entity.SnutCollection;
import com.curation.snut.entity.StoredData;
import com.curation.snut.repository.CurationImageRepository;
import com.curation.snut.repository.CurationRepository;
import com.curation.snut.repository.SnutCollectionRepository;
import com.curation.snut.repository.StoredDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class StoredDateServiceImpl implements StoredDataService{

    private final StoredDataRepository storedDataRepository;
    private final CurationRepository curationRepository;
    private final SnutCollectionRepository snutCollectionRepository;
    private final CurationService curationService;
    private final SnutCollectionService snutCollectionService;
    private final CurationImageRepository curationImageRepository;

    @Override
    public void storeCurationByCurationNo(Long id, String email, String cuCo) {
        StoredData storedData = StoredData.builder()
                .email(email)
                .cuCoId(id)
                .cuCo(cuCo)
                .build();
        storedDataRepository.save(storedData);
    }

    @Override
    public Map getStoredDataByEmail(String email) {
        List<Object[]> list = storedDataRepository.getStoredDataByEmail(email);

        System.out.println(list.size());

        List<SnutCollectionDTO> colList = new ArrayList<>();
        List<CurationDTO> cuList = new ArrayList<>();
        list.forEach(i -> {
            if(i[1].equals("Collection")) {
                System.out.println("Collection......." + i[0] + "  " + i[1]);
                Optional<SnutCollection> tempCol = snutCollectionRepository.findById((Long) i[0]);
                List<CurationDTO> cuDTOInCol = curationService.getCurationsByCollectionNo(tempCol.get().getCollectionNo());
                colList.add(snutCollectionService.colToColDTO(tempCol.get(), cuDTOInCol));
            } else {
                System.out.println("Curation......." + i[0] + "  " + i[1]);
                Optional<Curation> tempCu = curationRepository.findById((Long) i[0]);
                List<CurationImage> cuImgList = curationImageRepository.findFileDataByCurationNo(tempCu.get().getCurationNo());
                cuList.add(curationService.entityToDTO(tempCu.get(), cuImgList));
            }
        });
        System.out.println("colList......." + colList);
        System.out.println("cuList......." + cuList);
        Map result = new HashMap<>();
        result.put("Collection", colList);
        result.put("Curation", cuList);
        return result;
    }
}
