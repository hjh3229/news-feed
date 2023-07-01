package com.sparta.newsfeed.Common.security;

import com.sparta.newsfeed.Common.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;
    private UserDetailsServiceImpl userService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;

    }

    // websecurity 에서 설정한 url path에 인증이 필요할 때
    // 1)hearder에 실려온 token을 얻어와서
    // 유요한 토큰인지 검증.

    // 2)유요한 토큰내에 유저 정보를 가져와
    // 정보를 토대로 인증 권한을 줌,

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String tokenValue = jwtUtil.getJwtFromHeader(req);
        if (StringUtils.hasText(tokenValue)) {
                //return (str != null && !str.isEmpty() && containsText(str));
            if (!jwtUtil.validateToken(tokenValue)) {
                return;
            } //1)유요한 토큰인지 검증

            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
            //2)유요한 토큰에서 유저 정보를 가져옴
            try {
                setAuthentication(info.getSubject());
                // 인증 권한 부여
            } catch (Exception e) {
                return;
            }

        }
        filterChain.doFilter(req, res);
    }


    // 인증 권한 부여 방법

    // 검증이 완료된 유요한 token에서 가져온 유저 정보를 UserDetailsService 에 보내서
    // database에 저장된 유저인지 확인 후 UserDetails에 전달
    // UserDetails에 저장 된 정보로 인증 token 생성

    // 인증 token으로 인증 객체인 Authentication 을 생성
    // SecurityContextHolder에 저장한다.
    // ( 내부 동작 : -> SecurityContextHolder안에 SecurityContext에 인증 객체인 Authentication 저장 )

    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken Token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        return Token;
    }
}
