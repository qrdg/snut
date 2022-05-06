package com.curation.snut.service;

import com.curation.snut.dto.PageRequestDTO;
import com.curation.snut.dto.PageResultDTO;
import com.curation.snut.dto.SnutCollectionDTO;
import com.curation.snut.dto.CurationDTO;
import com.curation.snut.entity.Hashtag;
import com.curation.snut.entity.SnutCollection;
import com.curation.snut.entity.Curation;
import com.curation.snut.entity.Member;
import com.curation.snut.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface SnutCollectionService {

    // 성진
    List<SnutCollectionDTO> getCollectionsByEmail(String email);
    List<SnutCollectionDTO> getCollectionsByWord(String word);

    Long snutCollectionRegister(SnutCollectionDTO snutCollectionDTO);

    PageResultDTO getList(PageRequestDTO requestDTO);

    PageResultDTO getPopularCollection(PageRequestDTO requestDTO);

    default SnutCollectionDTO colToColDTO(SnutCollection collection, List<CurationDTO> Curations) {


//        System.out.println("colToColDTO >>>>>>>> " + Curations);
        SnutCollectionDTO dto = SnutCollectionDTO.builder()
                .collectionNo(collection.getCollectionNo())
                .collectionTitle(collection.getCollectionTitle())
                .collectionText(collection.getCollectionText())
                .email(collection.getWriter().getEmail())
                .hashtag(collection.getHashtag())
                .curationList(Curations)
                .nickname(collection.getWriter().getNickName())
                .like(collection.isLike())
                .regDate(collection.getRegDate())
                .modDate(collection.getModDate())
                .build();

        return dto;
    }

    default SnutCollection colDTOToCol(SnutCollectionDTO collectionDTO) {

        System.out.println("collectionDTO Service >>>> " + collectionDTO);
        Set<Hashtag> hashtag = (Set<Hashtag>) collectionDTO.getHashtag().stream().map(tag -> {
           return Hashtag.builder().tag(tag.toString()).build();
        }).collect(Collectors.toSet());

        System.out.println("collectionDTO Service after make hashtag >>>> " + collectionDTO);
        SnutCollection collection = SnutCollection.builder()
                .collectionNo(collectionDTO.getCollectionNo())
                .collectionTitle(collectionDTO.getCollectionTitle())
                .collectionText(collectionDTO.getCollectionText())
                .like(collectionDTO.isLike())
                .hashtag(hashtag)
                .writer(Member.builder().email(collectionDTO.getEmail()).build())
                .build();

        return collection;
    }

    /////////////////////////////////////////////////////////////////////////////////
}
