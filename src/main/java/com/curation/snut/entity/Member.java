package com.curation.snut.entity;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.*;

import com.curation.snut.security.role.MemberRole;

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
public class Member extends BaseEntity {
    @Id
    private String email;

    private String name;
    private String pw;
    private String mobile;
    private String birth;
    private String gender;
    private int memberLevel;

    @Column(unique = true)
    private String nickName;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void addMemberRole(MemberRole memberRole) {
        roleSet.add(memberRole);
    }

    public void confirmMemberLevel(int curationCount) {
        this.memberLevel = inspectCurationCount(curationCount);
    }

    public int inspectCurationCount(int curationCount) {
        if (curationCount < 10) return 1;
        else if (curationCount < 20) return 2;
        else if (curationCount < 50) return 3;
        else if (curationCount < 70) return 4;
        else if (curationCount < 100) return 5;
        else if (curationCount < 120) return 6;
        else if (curationCount < 150) return 7;
        else if (curationCount < 170) return 8;
        else if (curationCount < 200) return 9;
        else if (curationCount >= 200) return 10;
        else return 0;
    }
}