package com.curation.snut.security.service;

import java.util.Optional;
import java.util.stream.Collectors;

import com.curation.snut.entity.Member;
import com.curation.snut.repository.MemberRepository;
import com.curation.snut.security.dto.AuthMemberDTO;
import com.curation.snut.security.role.MemberRole;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
        private final MemberRepository repository;
        private final PasswordEncoder passwordEncoder;

        @Override
        public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

                log.info("CommonOAuth2UserDetailsService..userRequest:" + userRequest);
                String clientName = userRequest.getClientRegistration().getClientName();

                OAuth2User oAuth2User = super.loadUser(userRequest);

                String email = null;
                String nickName = null;
                if (clientName.equals("Google"))
                        email = oAuth2User.getAttribute("email");
                nickName = "소셜" + (int) (Math.random() * 1000000000) + 1;// oAuth2User.getAttribute("email");
                Member member = saveSocialMember(email, nickName);

                AuthMemberDTO authMemberDTO = new AuthMemberDTO(
                                member.getEmail(),
                                member.getName(),
                                member.getPw(),
                                member.getMobile(),
                                member.getBirth(),
                                member.getGender(),
                                member.getNickName(),
                                member.getRoleSet().stream()
                                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                                                .collect(Collectors.toList()),
                                oAuth2User.getAttributes());
                authMemberDTO.setName(member.getName());
                return authMemberDTO;
        }

        private Member saveSocialMember(String email, String nickName) {
                Optional<Member> result = repository.findByEmail(email);
                Optional<Member> result2 = repository.findByNickName(nickName);
                if (result.isPresent()) {
                        return result.get();
                }
                // 없다면
                if (result2.isPresent()) {
                        while (result2.isEmpty()) {
                                nickName = nickName + 1;
                        }
                        Member member = Member.builder()
                                        .email(email)
                                        .nickName(nickName)
                                        .pw(passwordEncoder.encode("1"))
                                        .build();
                        member.addMemberRole(MemberRole.USER);
                        repository.save(member);
                        return member;
                } else {
                        Member member = Member.builder()
                                        .email(email)
                                        .nickName(nickName)
                                        .pw(passwordEncoder.encode("1"))
                                        .build();
                        member.addMemberRole(MemberRole.USER);
                        repository.save(member);
                        return member;
                }

        }
}
