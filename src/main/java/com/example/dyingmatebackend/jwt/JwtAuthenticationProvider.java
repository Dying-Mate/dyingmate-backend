package com.example.dyingmatebackend.jwt;

import com.example.dyingmatebackend.user.CustomUserDetailsService;
import com.example.dyingmatebackend.user.User;
import com.example.dyingmatebackend.user.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider {

    @Value("${jwt.secret}")
    private String secretKey = "seceretKey";

    @Value("${jwt.access-token-time}")
    private long access_token_time;

    @Value("${jwt.refresh-token-time}")
    private long refresh_token_time;

    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;

    // Access Token 생성
    public String createAccessToken(Long userId, String name) {
        return createToken(userId, name, "Access", access_token_time);
    }

    // Refresh Token 생성
    public String createRefreshToken(Long userId, String name) {
        return createToken(userId, name, "Refresh", refresh_token_time);
    }

    public String createToken(Long userId, String name,
                              String type, Long tokenValidTime) {
        return Jwts.builder()
                .setHeaderParam("type", type) // Header 구성
                .setClaims(createClaims(userId, name)) // Payload - Claims 구성
                .setSubject(userId.toString()) // Payload - Subject 구성
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime))
                .compact();
    }

    public static Claims createClaims(Long userId, String name) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        claims.put("name", name);
        return claims;
    }

    // 권한 정보 확인
    public Authentication getAuthentication(String accessToken) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(this.getEmail(accessToken));

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        return token;
    }

    public String getEmail(String accessToken) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(accessToken)
                .getBody()
                .getSubject();
    }

    // Authorization Header를 통한 인증
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // access token 검증
    public boolean isExpiredToken(String accessToken) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken) // return 타입 Date
                .getBody().getExpiration().before(new Date()); // token이 Expired된 것이 지금보다 전이냐?
    }

    // 토큰으로부터 user id 받아오기
    public Long getUserId(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String accessToken = request.getHeader("Authorization").split(" ")[1].trim();
        String email = getEmail(accessToken);
        User user = userRepository.findByEmail(email).get();
        return user.getUserId();
    }
}
