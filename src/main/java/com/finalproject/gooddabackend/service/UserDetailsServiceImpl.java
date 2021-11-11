package com.finalproject.gooddabackend.service;

import com.finalproject.gooddabackend.model.User;
import com.finalproject.gooddabackend.repository.UserRepository;
import com.finalproject.gooddabackend.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    //로그인할 때 들어온 username으로 DB에서 정보 찾기
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + userEmail));

        return new UserDetailsImpl(user);
    }
}