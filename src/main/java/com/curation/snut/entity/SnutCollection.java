package com.curation.snut.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.*;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SnutCollection extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collectionNo;

    private String collectionTitle;
    private String collectionText;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    @Column(name = "collection_like")
    private boolean like;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Hashtag> hashtag = new HashSet<>();

    @JsonIgnoreProperties({"snut_collection"})
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MemberCollectionLike> colLikeList;


//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "curation_col_no")
//    private Set<Curation> curation = new LinkedHashSet<>();

}
