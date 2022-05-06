package com.curation.snut.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.curation.snut.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CurationDTO {
    private Long curationNo;

    private String email;
    private String nickname;

    private String curationTitle;
    private String curationText;
    private Long pickedEmoji;
    private String pickedColor;
    private boolean open;
    private boolean like;

    private Set hashtag;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
    // 검색용
    private String type;
    private String keyword;

    @Builder.Default
    private List<CurationImageDTO> imageDTOList = new ArrayList<>();

}
