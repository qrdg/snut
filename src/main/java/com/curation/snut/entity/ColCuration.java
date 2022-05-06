package com.curation.snut.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ColCuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coCuId;

    @ManyToOne
    @JoinColumn(name = "cu_col_id")
    private Curation curation;

    @ManyToOne
    @JoinColumn(name = "col_cu_id")
    private SnutCollection snutCollection;
}
