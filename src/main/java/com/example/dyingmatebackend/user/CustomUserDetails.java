package com.example.dyingmatebackend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    /* UserDetails: Spring Security가 제공하는 다양한 인증 및 인가 기능을 활용할 수 있도록 구조화된 사용자 정보를 제공
    * User 테이블에 바로 상속하지 않은 이유는 -> Entity가 오염되기 때문!
    * */

    private final User user;
    private String authority = "USER";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자에게 부여된 권한 목록을 반환
        List<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority(authority));
        return auth;
    }

    @Override
    public String getPassword() {
        return user.getPwd(); // 사용자의 암호화된 비밀번호 반환
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // 사용자의 고유 식별자 반환
    }

    @Override
    public boolean isAccountNonExpired() { // 사용자 계정이 만료되었는지
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 사용자 계정이 잠겨있는지
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 사용자 인증 정보가 만료되었는지
        return true;
    }

    @Override
    public boolean isEnabled() { // 사용자가 활성화되었는지
        return true;
    }
}
