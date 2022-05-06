package com.curation.snut.security.util;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.curation.snut.security.dto.AuthMemberDTO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JWTUtil {
    private String secretKey = "secretKey";
    private long expire = 60 * 24 * 30;

    public String generateToken(AuthMemberDTO content) throws Exception {
        return Jwts.builder()
                .setIssuedAt(new Date()).setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
                .claim("sub", content).signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8")).compact();
    }

    public Map validateAndExtract(String tokenStr) throws Exception {
        Map contentValue = null;
        try {
            DefaultJws defaultJws = (DefaultJws) Jwts.parser().setSigningKey(secretKey.getBytes("UTF-8"))
                    .parseClaimsJws(tokenStr);
            DefaultClaims claims = (DefaultClaims) defaultJws.getBody();
            // contentValue = claims.getSubject();
            System.out.println("claims >>>>> " + claims);
            Map subMap = new HashMap<>();
            subMap.put("sub", claims.get("sub"));
            contentValue = subMap;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            contentValue = null;
        }
        log.info("contentValue >>>>>>>>>>>> " + contentValue);
        return contentValue;
    }

    public Object getClaims(String tokenStr) throws Exception {
        Object contentValue = null;
        try {
            DefaultJws defaultJws = (DefaultJws) Jwts.parser().setSigningKey(secretKey.getBytes("UTF-8"))
                    .parseClaimsJws(tokenStr);
            DefaultClaims claims = (DefaultClaims) defaultJws.getBody();
            System.out.println("claims >>>>> " + claims);
            contentValue = claims.get("sub");
            log.info("contentValue >>>>>>>>> " + contentValue);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            contentValue = null;
        }
        return contentValue;
    }
}
