package com.example.wheather.service;

import com.example.wheather.dto.ResponseDto;
import com.example.wheather.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    @Value("${access.token.expiration.time}")
    private Long expiryDate;

    public ResponseEntity<?> jwtToken(String email, String password){
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            String jwt = jwtTokenUtil.jwtGenerator((UserDetails) authenticate.getPrincipal());
            return ResponseEntity.ok(getResponseModel(jwt));
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }

    }


    private ResponseDto getResponseModel(String jwt) {
        Map<String,Object> map = new HashMap<>();
        map.put("jwt",jwt);
        map.put("expiry_time",expiryDate);
        map.put("issued_at",new Date());
        return ResponseDto.getSuccess(map);
    }


}