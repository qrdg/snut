package com.curation.snut.security.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.curation.snut.dto.community.ApiSessionDTO;
import com.curation.snut.security.dto.AuthMemberDTO;
import com.curation.snut.security.util.JWTUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StreamUtils;

@Log4j2
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {
    private JWTUtil jwtUtil;

    public ApiLoginFilter(String defaultFilterProcessesUrl, JWTUtil jwtUtil) {
        super(defaultFilterProcessesUrl);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        log.info("------------ApiLoginFilter------------");

        // json 데이터 읽기
        ServletInputStream inputStream = request.getInputStream();
        String msgBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;

        try {
            jsonObject = (JSONObject) parser.parse(msgBody);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String email = jsonObject.get("email").toString();
        String pw = jsonObject.get("pw").toString();
        log.info("emai:" + email + " / pw:" + pw);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, pw);
        log.info("authToken::" + authToken.toString());

        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        log.info("ApiLoginFilter successfulAuthentication:" + authResult);
        log.info("Principal:" + authResult.getPrincipal());
        // String email = ((AuthMemberDTO) authResult.getPrincipal()).getUsername();
        AuthMemberDTO userDtail = ((AuthMemberDTO) authResult.getPrincipal());

        String token = null;
        ObjectMapper mapper = new ObjectMapper();
        String curl = "";
        try {
            // token = "Bearer "+jwtUtil.generateToken(email);
            token = "Bearer " + jwtUtil.generateToken(userDtail);
            ApiSessionDTO apiDto = commonDtoToApiDTO((AuthMemberDTO) authResult.getPrincipal(), token, curl);
            String res = mapper.writeValueAsString(token);
            log.info("res:::" + res);
            response.setContentType("application/json");
            response.getOutputStream().write(res.getBytes());
            log.info("token=> " + token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ApiSessionDTO commonDtoToApiDTO(AuthMemberDTO commonDto, String token, String curl) {
        ApiSessionDTO apiDto = ApiSessionDTO.builder()
                .email(commonDto.getEmail()).token(token).name(commonDto.getName())
                .birth(commonDto.getBirth()).gender(commonDto.getGender()).nickname(commonDto.getNickName())
                .mobile(commonDto.getMobile()).curl(curl)
                .username(commonDto.getUsername())
                .build();
        return apiDto;
    }
}
