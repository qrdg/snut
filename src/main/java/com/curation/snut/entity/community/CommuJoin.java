package com.curation.snut.entity.community;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

import com.curation.snut.entity.Member;
import com.curation.snut.idclass.CommuJoinId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@IdClass(CommuJoinId.class)
public class CommuJoin {

    @JsonIgnore
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Member jMember;

    @JsonIgnore
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Community jCommunity;

}
