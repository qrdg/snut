package com.curation.snut.service;

import java.util.*;
import java.util.stream.Collectors;

import com.curation.snut.dto.CurationDTO;
import com.curation.snut.dto.CurationImageDTO;
import com.curation.snut.dto.PageRequestDTO;
import com.curation.snut.dto.SnutCollectionDTO;
import com.curation.snut.entity.Curation;
import com.curation.snut.entity.CurationImage;
import com.curation.snut.entity.Hashtag;
import com.curation.snut.entity.Member;

import com.curation.snut.repository.HashtagRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface CurationService {

    // 성진
    List<CurationDTO> getCurationByEmail(String email);
    void setMemberLevel(String email);
    CurationDTO getCurationsByCurationNo(Long cuId);
    List<CurationDTO> getCurationsByCollectionNo(Long colId);
    List<CurationDTO> getCurationsByWord(String word);
    void deleteCurationById(Long no);


    Long register(CurationDTO curationDTO);
    ///////////////////////////////////////////////////////////////////////////

//    public List<CurationDTO> CuList();
//
//    public List<CurationDTO> searchCurationTitle(String searchCurationTitle);
//
//    CurationDTO getCuration(Long curationNo);

//    void write(CurationDTO curationDTO);

//    void modify(CurationDTO curationDTO);

//    void delete(Long id);

    @Transactional
    default Map<String, Object> dtoToEntity(CurationDTO dto) {

        Map<String, Object> entitMap = new HashMap<>();

        System.out.println("dtoToEntity ------------------------------");
        System.out.println("hashtag >>>> " + dto.getHashtag());

        Set<Hashtag> hashtagSet = new HashSet<>();

        if(dto.getHashtag() != null && dto.getHashtag().size() > 0) {
            dto.getHashtag().forEach(tag -> {
                hashtagSet.add(Hashtag.builder().tag((String) tag).build());
            });
        }

        System.out.println("hashtagSet >>>>> " + hashtagSet);

        Curation curation = Curation.builder()
                .curationNo(dto.getCurationNo())
                .curationText(dto.getCurationText())
                .curationTitle(dto.getCurationTitle())
                .pickedColor(dto.getPickedColor())
                .pickedEmoji(dto.getPickedEmoji())
                .open(dto.isOpen())
                .like(dto.isLike())
                .hashtag(hashtagSet)
                .writer(Member.builder().email(dto.getEmail()).build())
                .build();

        entitMap.put("curation", curation);

        List<CurationImageDTO> imageDTOList = dto.getImageDTOList();

        if(imageDTOList != null && imageDTOList.size() > 0) {
            List<CurationImage> curationImageList = imageDTOList.stream()
                    .map(curationImageDTO -> {

                        CurationImage curationImage = CurationImage.builder()
                                .path(curationImageDTO.getPath())
                                .imageName(curationImageDTO.getImgName())
                                .uuid(curationImageDTO.getUuid())
                                .curation(curation)
                                .build();
                        return curationImage;
                    }).collect(Collectors.toList());
            entitMap.put("imgList", curationImageList);
        }

        return entitMap;
    }

    default CurationDTO entityToDTO(Curation curation, List<CurationImage> curationImages) {
        CurationDTO curationDTO = CurationDTO.builder()
                .curationNo(curation.getCurationNo())
                .curationText(curation.getCurationText())
                .curationTitle(curation.getCurationTitle())
                .email(curation.getWriter().getEmail())
                .pickedColor(curation.getPickedColor())
                .pickedEmoji(curation.getPickedEmoji())
                .hashtag(curation.getHashtag())
                .nickname(curation.getWriter().getNickName())
                .regDate(curation.getRegDate())
                .modDate(curation.getModDate())
                .open(curation.isOpen())
                .like(curation.isLike())
                .build();

        List<CurationImageDTO> curationImageDTO = curationImages.stream().map(img -> {
           return CurationImageDTO.builder()
                   .ciId(img.getCiid())
                   .path(img.getPath())
                   .uuid(img.getUuid())
                   .imgName(img.getImageName())
                   .build();
        }).collect(Collectors.toList());

        curationDTO.setImageDTOList(curationImageDTO);

        return curationDTO;
    }
}
