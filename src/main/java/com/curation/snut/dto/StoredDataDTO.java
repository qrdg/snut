package com.curation.snut.dto;

import com.curation.snut.entity.Member;
import lombok.*;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoredDataDTO {

    private Long sid;
    private Member member;
    private CurationDTO curation;
    private SnutCollectionDTO snutCollectionDTO;

}
