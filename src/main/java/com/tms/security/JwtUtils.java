package com.tms.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.channels.UnsupportedAddressTypeException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateJwtToken(String login) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", login);
        //Map<String, Object> claims2 = new HashMap<>();
        return Jwts.builder()
                .setSubject("UserDetails")
                .setClaims(claims)
                .setExpiration(new Date((new Date().getTime() + expiration)))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.info("Invalid JWT signature: " + e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token: " + e);
        } catch (UnsupportedAddressTypeException e) {
            log.info("Unsupported JWT token: " + e);
        } catch (IllegalArgumentException e) {
            log.info("Illegal arguments: " + e);
        }
        return false;
    }

    public String getTokenFromHttpRequest(HttpServletRequest request) {
        final String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getLoginFromJwt(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
                    .getBody().get("username").toString();
        } catch (Exception e) {
            log.info("Can't take login from JWT: " + e);
        }
        return null;
    }
}
