package com.curation.snut.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "curation")
public class CurationImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ciid;

    private String uuid;
    private String imageName;
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Curation curation;

}
