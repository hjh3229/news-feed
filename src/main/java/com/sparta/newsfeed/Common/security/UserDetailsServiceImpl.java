package com.sparta.newsfeed.Common.security;

import com.sparta.newsfeed.User.entity.User;
import com.sparta.newsfeed.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    //UserDetailService interface 구현하는 구현체 생성.
    //인증된 객체 토큰을 통해, db에 접근하하여 user 정보를 userDetail에 전달하는 역할.
    //하단 override method도 객체 토큰에서 전달 받은 유저 내임을 통해 db에서 유저 객체를 받아와
    //detail에 전달.

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + username));
        return  new UserDetailsImpl(user);
    }
}
