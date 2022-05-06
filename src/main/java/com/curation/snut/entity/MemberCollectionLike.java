package com.curation.snut.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class MemberCollectionLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mcolId;

    @ManyToOne
    private Member member;

    @ManyToOne
    private SnutCollection snutCollection;

    @Builder
    public void Likes(SnutCollection snutCollection, Member member) {
        this.snutCollection = snutCollection;
        this.member = member;
    }
}