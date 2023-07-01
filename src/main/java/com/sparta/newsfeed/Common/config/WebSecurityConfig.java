package com.sparta.newsfeed.Common.config;

import com.sparta.newsfeed.Common.jwt.JwtUtil;
import com.sparta.newsfeed.Common.security.JwtAuthenticationFilter;
import com.sparta.newsfeed.Common.security.JwtAuthorizationFilter;
import com.sparta.newsfeed.Common.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security 지원
@RequiredArgsConstructor
public class WebSecurityConfig {
    // Spring Security   Fitter 기반

    // 기억해야 할것
    // Security 필터를 통해 인증 및 허가

    // 1) name,password 필터에서 name,password 확인 후, token(인증 토큰)(not jwt) 발급하여,
    // {->  bean 생성(jwt방식이기 때문에 따로 상속 받아 다시 만듬)}

    // 2) 인증 매니저에게 전달 (인증 매니저는 인증 토큰을 가지고 인증절차를 진행 후 JWT 토큰 발급)
    //{ -> 인증매니저 bean 생성 -> name,password 필터안에 매니져를 넣어준다 생각}

    // 3) 클라이언트에게서 요청받은 JWT검증을 하기 위한 필터 생성
    // {->  bean 생성(jwt방식이기 때문에 따로 상속 받아 다시 만듬)}

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userService;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
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
    } //허가 필터 객체 생성




    // SecurityFilterChain 생성 (securityfilter setting)
    // CSRF?
    // JWT 인증 방식 Or session
    // path 허가 권한
    // 로그인 페이지(formlogin) 설정 (js)
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
                        .requestMatchers("/newsfeed/user/**").permitAll() // 회원가입, 로그인 요청 모두 접근 허가
                        .requestMatchers("/newsfeed/feeds/**").permitAll() // Feed 조회 요청 모두 접근 허가
                        .requestMatchers("/newsfeed/folders/**").permitAll() // folder 조회 요청 모두 접근 허가
                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리

        );

//        http.formLogin((formLogin) ->
//                formLogin
//                        .loginPage("/api/user/login-page").permitAll()
//        );



        // 필터 순서 관리
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }





}

