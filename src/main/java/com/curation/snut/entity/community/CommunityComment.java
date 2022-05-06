package com.curation.snut.entity.community;

import javax.persistence.*;

import com.curation.snut.entity.BaseEntity;
import com.curation.snut.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class CommunityComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;
    private String text;
    private Long parentNo;
    private boolean announcement;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Community communityName;
}