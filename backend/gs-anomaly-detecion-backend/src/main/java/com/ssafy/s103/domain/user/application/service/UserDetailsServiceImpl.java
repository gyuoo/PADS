package com.ssafy.s103.domain.user.application.service;

import com.ssafy.s103.domain.user.application.repository.UserRepository;
import com.ssafy.s103.domain.user.entity.User;
import com.ssafy.s103.domain.user.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException(email));
        return new UserDetailsImpl(user);
    }
}
