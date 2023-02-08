package com.example.wheather.rest;

import com.example.wheather.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")

public class TokenController {
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> getToken(@RequestParam String username, @RequestParam String password){
        return tokenService.jwtToken(username, password);
    }
}
