package com.example.dyingmatebackend.config;

import com.example.dyingmatebackend.jwt.JwtAuthenticationFilter;
import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    // 인증과 인가를 담당 (필터를 통해 인증 방식과 인증 절차에 대해 등록하여 설정 담당)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // configure HTTP security
        http.httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable) // 서버에 인증정보를 저장하지 않기에 csrf 사용 X
                .cors(Customizer.withDefaults())
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Session 기반의 인증기반을 사용하지 않고 추후 JWT를 이용하여 인증 예정
                .authorizeHttpRequests(authorize -> { // 요청에 대한 권한 설정
                    authorize.requestMatchers("/user/**").permitAll()
                            .anyRequest().authenticated();
                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtAuthenticationProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
