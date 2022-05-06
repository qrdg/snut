package com.curation.snut.entity;

import com.curation.snut.dto.CurationImageDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "writer")
public class Curation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long curationNo;

    private String curationTitle;

    @Column(length = 50000)
    private String curationText;

    private Long pickedEmoji;
    @Nullable
    private String pickedColor;

    @Nullable
    private boolean open;

    @Column(name = "cu_like")
    private boolean like;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Hashtag> hashtag = new HashSet<>();

    @JsonIgnoreProperties({"curation"})
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MemberCurationLike> cuLikeLIst;

    public void changeCurationTitle(String curationTitle) {
        this.curationTitle = curationTitle;
    }

    public void changeCurationText(String curationText) {
        this.curationText = curationText;
    }
}