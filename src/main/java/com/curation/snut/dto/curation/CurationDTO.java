package com.curation.snut.dto.curation;

import java.time.LocalDateTime;

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
    private String curationTitle;
    private String email;
    private String curationText;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private boolean open;
    // 검색용
    private String type;
    private String keyword;

    private Long stickerNo;

}
