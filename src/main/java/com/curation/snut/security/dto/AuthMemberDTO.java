package com.curation.snut.security.dto;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthMemberDTO extends User implements OAuth2User {
    private String email;
    private String name;
    private String mobile;
    private String birth;
    private String gender;
    private String nickName;
    private Map<String, Object> attr;

    public AuthMemberDTO(
            String username, String name, String password,
            String mobile, String birth, String gender, String nickName,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.name = name;
        this.mobile = mobile;
        this.birth = birth;
        this.gender = gender;
        this.nickName = nickName;
    }

    public AuthMemberDTO(
            String username, String name, String password,
            String mobile, String birth, String gender, String nickName,
            Collection<? extends GrantedAuthority> authorities,
            Map<String, Object> attr) {
        this(username, name, password, mobile, birth, gender, nickName, authorities);
        this.attr = attr;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }

}
