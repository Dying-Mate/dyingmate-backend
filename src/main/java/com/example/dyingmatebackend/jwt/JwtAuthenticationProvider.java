package com.example.dyingmatebackend.jwt;

import com.example.dyingmatebackend.user.CustomUserDetailsService;
import com.example.dyingmatebackend.user.UserRequestDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider {

    // @Value("${jwt.secret}")
    private static String secretKey = "secretKey";

    private final CustomUserDetailsService customUserDetailsService;

    public static String createAccesssToken(UserRequestDto userRequestDto) {
        return Jwts.builder()
                .setHeader(createHeader()) // Header 구성
                .setClaims(createClaims(userRequestDto)) // Payload - Claims 구성
                .setSubject(userRequestDto.getEmail()) // Payload - Subject 구성
                .signWith(SignatureAlgorithm.HS256, secretKey) // Signature 구성
                .setExpiration(createExpiredDate()) // Expired Date 구성
                .compact();
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");

        return header;
    }

    private static Map<String, Object> createClaims(UserRequestDto userRequestDto) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("email", userRequestDto.getEmail());
        claims.put("pwd", userRequestDto.getPwd());

        return claims;
    }

    private static Date createExpiredDate() { // 토큰 만료 시간 지정 (한 달)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 30);
        return calendar.getTime();
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
}
