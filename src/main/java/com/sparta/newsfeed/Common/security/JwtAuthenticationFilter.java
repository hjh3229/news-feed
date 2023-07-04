package com.sparta.newsfeed.Common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.newsfeed.Common.dto.LoginRequestDto;
import com.sparta.newsfeed.Common.dto.LoginResponseDto;
import com.sparta.newsfeed.Common.jwt.JwtUtil;
import com.sparta.newsfeed.User.entity.UserRoleEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/newsfeed/user/log-in");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            log.info("로그인 시도");
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword(), null);
            AuthenticationManager authenticationManager = getAuthenticationManager();
            Authentication authentication = authenticationManager.authenticate(token);
            return authentication;

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }
    // 1) name,password 필터에서 name,password 확인 후, 인증 token(not jwt) 발급하여
    // 2) 인증 매니저에게 전달
    // 3) 인증 매니저는 토큰을 가지고 인증절차를 진행 한다
    // 3-1) token의 아이디 정보를 UserDetailsService 에게 전달하고 회원 아이디 확인
    // 3-2) db에 저장된 유저 정보를 가져와 UserDetail 에게 전달
    // 3-3) 매니저가 알아서 userdetail 의 정보를 가지고 요청받은 id,password를 비교하여 인증함.


    // 4) 매니저는 인증 완료되면 인증 token으로 인증 객체인 Authentication 을 생성.
    // (4-1) 생성된 Authentication 는 SecurityContextHolder 에 저장됨)


    // 5) SecurityContextHolder에 저장된 인증 객체 Authentication에서의 인증된 정보로 jwt token을 만듬
    // response 헤더에 넣고 클라이언트에게 전달.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        log.info("로그인 성공 및 JWT 생성");
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(username, role);

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        LoginResponseDto loginRequestDto = jwtUtil.loginSuccess();
        response.getOutputStream().println(loginRequestDto.getMsg());
        response.getOutputStream().println("status Code : "+loginRequestDto.getStatusCode());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        log.info("로그인 실패");
        response.setStatus(401);
    }

}
