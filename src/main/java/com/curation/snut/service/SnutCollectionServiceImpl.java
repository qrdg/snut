package com.curation.snut.service;

import com.curation.snut.dto.CurationDTO;
import com.curation.snut.dto.PageRequestDTO;
import com.curation.snut.dto.PageResultDTO;
import com.curation.snut.dto.SnutCollectionDTO;
import com.curation.snut.entity.*;
import com.curation.snut.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class SnutCollectionServiceImpl implements SnutCollectionService {

    private final SnutCollectionRepository snutCollectionRepository;
    private final ColCurationRepository colCurationRepository;
    private final CurationRepository curationRepository;
    private final CurationService curationService;


    @Override
    public List<SnutCollectionDTO> getCollectionsByEmail(String email) {
        List<SnutCollection> colList = snutCollectionRepository.findSnutCollectionByEamil(email);
        return colList.stream().map(i -> {
            return makeCurationDTOwithCurationImage(i);
        }).collect(Collectors.toList());
    }

    @Override
    public List<SnutCollectionDTO> getCollectionsByWord(String word) {
        List<SnutCollection> collections = snutCollectionRepository.findCurationByWord(word);

        return collections.stream().map(i -> {
            return makeCurationDTOwithCurationImage(i);
        }).collect(Collectors.toList());
    }

    // Collection Entity를 넣어주면 알아서 관련 Curation과 CurationImage 정보를 합쳐서 CollectionDTO로 만들어주는 메서드
    public SnutCollectionDTO makeCurationDTOwithCurationImage(SnutCollection col) {
        List<CurationDTO> cuDTOList = curationService.getCurationsByCollectionNo(col.getCollectionNo());
        List<Long> cuIdList = colCurationRepository.getCurationNoListByCollectionNo(col.getCollectionNo());

        System.out.println("cuDTOList >>>>> " + cuDTOList);
        System.out.println("cuDTOList >>>>>> " + cuDTOList);
        List<CurationDTO> finalCuDTOList = cuDTOList.stream().filter(j -> {
            return cuIdList.contains(j.getCurationNo());
        }).collect(Collectors.toList());

        return colToColDTO(col, finalCuDTOList);
    }

    @Transactional
    @Override
    public Long snutCollectionRegister(SnutCollectionDTO snutCollectionDTO) {

        SnutCollection snutCollection = colDTOToCol(snutCollectionDTO);
        log.info("snutCollection >>>>" + snutCollection);
        snutCollectionRepository.save(snutCollection);

        snutCollectionDTO.getCurations().stream().forEach(cuId -> {
            Optional<Curation> curation = curationRepository.findById(cuId);
           ColCuration colCuration = ColCuration.builder()
                   .snutCollection(snutCollection)
                   .curation(curation.get())
                   .build();

           colCurationRepository.save(colCuration);
        });

        return snutCollection.getCollectionNo();
    }

    @Override
    public PageResultDTO getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("collectionNo").descending());
        Page<SnutCollection> result = snutCollectionRepository.findAll(pageable);
        Function<SnutCollection, SnutCollectionDTO> fn = (en -> {
            List<Curation> cuList = curationRepository.findCurationsByCollectionNo(en.getCollectionNo());
            List<CurationDTO> cuDTOList = cuList.stream().map(cu -> {
                List<CurationImage> cuImgList = curationRepository.findCurationImageByCurationNo(cu.getCurationNo());
                return curationService.entityToDTO(cu, cuImgList);
            }).collect(Collectors.toList());
            return colToColDTO(en, cuDTOList);
        });

        return new PageResultDTO(result, fn);
    }

    @Override
    public PageResultDTO getPopularCollection(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("collection_no").descending());
        Page<SnutCollection> result = snutCollectionRepository.findSnutCollectionByHashTagCount(pageable);
        Function<SnutCollection, SnutCollectionDTO> fn = (en -> {
            List<Curation> cuList = curationRepository.findCurationsByCollectionNo(en.getCollectionNo());
            List<CurationDTO> cuDTOList = cuList.stream().map(cu -> {
                List<CurationImage> cuImgList = curationRepository.findCurationImageByCurationNo(cu.getCurationNo());
                return curationService.entityToDTO(cu, cuImgList);
            }).collect(Collectors.toList());
            return colToColDTO(en, cuDTOList);
        });

        return new PageResultDTO(result, fn);
    }
}
