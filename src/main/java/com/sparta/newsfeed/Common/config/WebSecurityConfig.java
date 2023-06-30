package com.sparta.newsfeed.Common.config;

import com.sparta.newsfeed.Common.jwt.JwtUtil;
import com.sparta.newsfeed.Common.security.JwtAuthenticationFilter;
import com.sparta.newsfeed.Common.security.JwtAuthorizationFilter;
import com.sparta.newsfeed.Common.security.UserDetailsImpl;
import com.sparta.newsfeed.Common.security.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
@RequiredArgsConstructor
public class WebSecurityConfig {
    // Spring Security
    // Fitter 기반
    // 기억해야 할것
    // Security 필터를 통해 인증 및 허가
    // 1) name,password 필터에서 name,password 확인 후, token(인증 토큰)(not jwt) 발급하여  (name,password 필터 생성)
    // 2) 인증 매니저에게 전달                                                  (인증매니저 bean 생성 -> name,password 필터에 넣어줌)
    // 3) 인증 매니저는 토큰을 가지고 인증절차를 진행 함.
    // -> 인증 완료 -> SecurityContextHolder안에 SecurityContext에 회원 정보(entity) 저장 ({UserDetail->(principal)}이 Holder안에 저장. )
    // -> UserDetailsService-> Repository조회 -> data -> UserDetail


    private final JwtUtil jwtUtil;
    private final UserServiceImpl userService;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //ioc container bean 생성
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
       return authenticationConfiguration.getAuthenticationManager();
    }// AuthenticationConfiguration (인증 환경설정) 을 받아서, AuthenticationManager 를 생성.

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    } //인증필터 객체 생성  // 필터 내에 위에서 만든 매니저 세팅

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userService);
    } //인가필터 객체 생성



    // SecurityFilterChain 생성 (securityfilter setting)
    // CSRF?
    // JWT 인증 방식 Or session
    // path 허가 권한
    // 로그인 페이지 설정 (js)
    // filter 순서 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
                        .requestMatchers("/").permitAll() // 메인 페이지 요청 허가
                        .requestMatchers("/api/user/**").permitAll() // '/api/user/'로 시작하는 요청 모두 접근 허가
                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리
        );

//        http.formLogin((formLogin) ->
//                formLogin
//                        .loginPage("/api/user/login-page").permitAll()
//        );

        // 필터 관리
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }





}

