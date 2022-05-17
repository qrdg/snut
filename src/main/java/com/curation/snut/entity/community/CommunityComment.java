package com.curation.snut.entity.community;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.curation.snut.entity.BaseEntity;
import com.curation.snut.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
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