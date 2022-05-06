package com.curation.snut.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class MemberCurationLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mcuId;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Curation curation;

    @Builder
    public void Likes(Curation curation, Member member) {
            this.curation = curation;
            this.member = member;
    }
}
