package com.curation.snut.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.curation.snut.controller.UploadController;
import com.curation.snut.dto.CurationDTO;
import com.curation.snut.entity.*;
import com.curation.snut.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CurationServiceImpl implements CurationService {
    private final CurationRepository curationRepository;
    private final CurationImageRepository curationImageRepository;
    private final HashtagRepository hashtagRepository;
    private final UploadController uploadController;
    private final MemberRepository memberRepository;
    // 성진

    @Override
    public List<CurationDTO> getCurationByEmail(String email) {
        List<Curation> curations = curationRepository.findCurationByEmail(email);

        return curations.stream().map(cu -> {
            List<CurationImage> imageList = curationRepository.findCurationImageByCurationNo(cu.getCurationNo());
            return entityToDTO(cu, imageList);
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CurationDTO getCurationsByCurationNo(Long curationNo) {
        Curation curation = curationRepository.findCurationsByCurationNo(curationNo);
        List<CurationImage> curationImageList = curationRepository.findCurationImageByCurationNo(curationNo);

        System.out.println("curation >>> " + curation);
        for(CurationImage ci : curationImageList) {
            System.out.println("curationImageList >>> " + ci);
        }

        return entityToDTO(curation, curationImageList);
    }

    @Modifying
    @Transactional
    @Override
    public void deleteCurationById(Long no) {

        List<CurationImage> cuImg = curationImageRepository.findFileDataByCurationNo(no);
        cuImg.forEach(img -> {
            try {
                String fileName = URLEncoder.encode(img.getPath()+"/"+img.getUuid()+"_"+img.getImageName(), "UTF-8");
                String sFileName = URLEncoder.encode(img.getPath()+"/s_"+img.getUuid()+"_"+img.getImageName(), "UTF-8");
                uploadController.removeFiles(fileName);
                uploadController.removeFiles(sFileName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        curationImageRepository.deleteByCurationNo(no);
        curationRepository.deleteByCurationNo(no);
    }



    @Transactional
    @Override
    public List<CurationDTO> getCurationsByCollectionNo(Long colId) {
        List<Curation> cuList = curationRepository.findCurationsByCollectionNo(colId);
        return cuList.stream().map(cu -> {
           List<CurationImage> curationImageList = curationRepository.findCurationImageByCurationNo(cu.getCurationNo());
           return entityToDTO(cu, curationImageList);
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<CurationDTO> getCurationsByWord(String word) {
        List<Curation> cuList = curationRepository.findCurationByWord(word);

        return cuList.stream().map(cu -> {
            List<CurationImage> imgList = curationRepository.findCurationImageByCurationNo(cu.getCurationNo());
            return entityToDTO(cu, imgList);
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void setMemberLevel(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        member.get().confirmMemberLevel(curationRepository.getCountCurationByEmail(email));
        memberRepository.save(member.get());
    }


    @Transactional
    @Override
    public Long register(CurationDTO curationDTO) {

        Map<String, Object> entityMap = dtoToEntity(curationDTO);
        Curation curation = (Curation) entityMap.get("curation");
        List<CurationImage> curationImageList = (List<CurationImage>) entityMap.get("imgList");

        Set hashtag = curation.getHashtag();
        if(hashtag.size() > 0) {
            hashtag.stream().forEach(tag -> hashtagRepository.save((Hashtag) tag));
        }

        if(curationImageList != null && curationImageList.size() > 0) {
            curationImageList.forEach(curationImage ->
                curationImageRepository.save(curationImage));
        }
        return curation.getCurationNo();
    }

}
