package com.example.wheather.security;

import com.example.wheather.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        String username =null;
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                username = jwtTokenUtil.getEmailFromJwt(token);
            } catch (IllegalArgumentException e) {
                System.out.println("Not Valid");
            } catch (ExpiredJwtException e) {
                System.out.println("Expired JWT token");
            } catch (Exception e) {
                System.out.println("Something wrong");
            }
            if (username != null && jwtTokenUtil.checkExpireDate(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
        }
        filterChain.doFilter(request,response);
    }
}
