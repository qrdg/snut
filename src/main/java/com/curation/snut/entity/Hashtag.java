package com.curation.snut.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Hashtag extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hid;

    private String tag;

    private long count;

    public void upCount() {
        count++;
    }

}
