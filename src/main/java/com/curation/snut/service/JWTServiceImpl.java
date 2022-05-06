package com.curation.snut.service;

import java.util.Map;

import com.curation.snut.security.util.JWTUtil;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {
    private final JWTUtil jwtUtil;

    public Map code(Map header) {
        String token = header.get("token").toString();
        Map userDetail = null;
        try {
            userDetail = jwtUtil.validateAndExtract(token.substring(7));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDetail;
    }
}
