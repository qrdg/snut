package com.curation.snut.entity.curation;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.curation.snut.entity.BaseEntity;
import com.curation.snut.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private String curationText;
    private boolean open;
    private Long stickerNo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;
    private Long viewCount;

    @OneToMany(mappedBy = "curation", cascade = CascadeType.ALL)
    Set<CurationLike> likes = new HashSet<>();

    public void changeCurationTitle(String curationTitle) {
        this.curationTitle = curationTitle;
    }

    public void changeCurationText(String curationText) {
        this.curationText = curationText;
    }
}
