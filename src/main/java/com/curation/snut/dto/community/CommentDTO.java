package com.curation.snut.dto.community;

import java.time.LocalDateTime;

import com.curation.snut.entity.Member;
import com.curation.snut.entity.community.Community;

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
public class CommentDTO {
    private Long cno;
    private String text;
    private boolean announcement;
    private Long parentNo;
    private Member writer;
    private Community communityName;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
