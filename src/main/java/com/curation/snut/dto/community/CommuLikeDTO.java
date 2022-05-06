package com.curation.snut.dto.community;

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
public class CommuLikeDTO {
    private Member member;
    private Community community;
}
