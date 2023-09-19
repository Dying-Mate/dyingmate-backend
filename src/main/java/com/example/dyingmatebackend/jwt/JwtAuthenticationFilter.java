package com.example.dyingmatebackend.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter { // 클라이언트에서 요청 시 JWT를 인증하기 위한 필터

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // token 꺼내기
            String authentication = jwtAuthenticationProvider.resolveToken(request);
            log.info("Authorization : {}", authentication);

            // token이 존재하지 않으면 Block
            if (authentication == null || !authentication.startsWith("Bearer ")) {
                log.error("Authorization을 잘못 보냈습니다.");
                filterChain.doFilter(request, response);
                return;
            }

            String accessToken = authentication.split(" ")[1].trim();
            // token 만료되었는지
            if (jwtAuthenticationProvider.isExpiredToken(accessToken)) {
                log.error("토큰이 만료되었습니다.");
                filterChain.doFilter(request, response);
                return;
            }

            // 권한 부여
            Authentication auth = jwtAuthenticationProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } catch (MalformedJwtException e) {
            log.error("손상된 토큰입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 토큰입니다.");
        } catch (SignatureException e) {
            log.error("시그니처 검증에 실패한 토큰입니다.");
        }
    }
}
