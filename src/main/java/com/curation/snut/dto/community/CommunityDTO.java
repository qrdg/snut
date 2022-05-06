package com.curation.snut.dto.community;

import java.time.LocalDateTime;

import com.curation.snut.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunityDTO {
    private Long no;
    private String title;
    private String thumbnail;
    private String text;
    private Member creater;

    private int replyCount;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
