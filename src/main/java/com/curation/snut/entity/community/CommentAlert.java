package com.curation.snut.entity.community;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class CommentAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ano;
    private String nickName;
    private String commuName;
    private String text;
}
