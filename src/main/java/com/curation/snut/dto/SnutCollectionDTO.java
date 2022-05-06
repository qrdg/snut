package com.curation.snut.dto;

import com.curation.snut.entity.Curation;
import com.curation.snut.entity.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SnutCollectionDTO {

    private Long collectionNo;
    private String collectionTitle;
    private String collectionText;

    private Set hashtag;

    private String email;
    private String nickname;
    private boolean like;

    private List<Long> curations;
    private List<CurationDTO> curationList;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

}
