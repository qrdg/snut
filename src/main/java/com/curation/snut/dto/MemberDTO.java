package com.curation.snut.dto;

import java.time.LocalDateTime;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private String email;

    private String name;
    private String pw;
    private String mobile;
    private String birth;
    private LocalDateTime modDate;
    private LocalDateTime regDate;
    private String nickname;
    private String gender;
    private boolean fromSocial;

    private Collection<String> roleSet;

}
