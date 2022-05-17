package com.curation.snut.service.curation;

import java.util.List;

import com.curation.snut.dto.curation.CurationDTO;
import com.curation.snut.entity.Member;
import com.curation.snut.entity.curation.Curation;

import org.springframework.stereotype.Service;

@Service

public interface CurationService {
    public List<CurationDTO> CuList();

    public List<CurationDTO> searchCurationTitle(String searchCurationTitle);

    CurationDTO getCuration(Long curationNo);

    void write(CurationDTO curationDTO);

    void modify(CurationDTO curationDTO);

    void delete(Long id);

    default Curation dtoToEntity(CurationDTO curationDTO) {
        Member member = Member.builder()
                .email(curationDTO.getEmail())
                .build();

        Curation curation = Curation.builder()
                .curationNo(curationDTO.getCurationNo())
                .curationText(curationDTO.getCurationText())
                .curationTitle(curationDTO.getCurationTitle())
                .open(curationDTO.isOpen())
                .writer(member)
                .stickerNo(curationDTO.getStickerNo())
                .build();

        return curation;
    }

    default CurationDTO entityToDTO(Curation curation) {
        CurationDTO curationDTO = CurationDTO.builder()
                .curationNo(curation.getCurationNo())
                .curationText(curation.getCurationText())
                .curationTitle(curation.getCurationTitle())
                .email(curation.getWriter().getEmail())
                .regDate(curation.getRegDate())
                .modDate(curation.getModDate())
                .build();
        return curationDTO;
    }
}
