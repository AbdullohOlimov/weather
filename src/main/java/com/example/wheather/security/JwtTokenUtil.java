package com.example.wheather.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
    @Value("${secret.key}")
    private String key;
    @Value("${access.token.expiration.time}")
    private long expirationTime;

    public String jwtGenerator(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+expirationTime))
                .signWith(SignatureAlgorithm.HS256,key).compact();
    }
    public boolean checkExpireDate(String token){
        Claims body = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        Date expiration = body.getExpiration();
        return expiration.getTime()>=new Date().getTime();

    }
    public String getEmailFromJwt(String token) throws SignatureException {
        Claims body = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return body.getSubject();
    }
}
