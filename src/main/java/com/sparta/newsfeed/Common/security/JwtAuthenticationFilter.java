package com.sparta.newsfeed.Common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.newsfeed.Common.dto.LoginRequestDto;
import com.sparta.newsfeed.Common.jwt.JwtUtil;
import com.sparta.newsfeed.User.entity.UserRoleEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword(), null);
            return getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    // 1) name,password 필터에서 name,password 확인 후, 어찌보면 인증 객체 token(not jwt) 발급하여
    // 2) 인증 매니저에게 전달
    // 3) 인증 매니저는 토큰을 가지고 인증절차를 진행 함.
    // -> 인증 완료 -> SecurityContextHolder안에 SecurityContext에 회원 정보(entity) 저장 ({UserDetail->(principal)}이 Holder안에 저장. )
    // -> UserDetailsService-> Repository조회 -> data -> UserDetail


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(username, role);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

}
