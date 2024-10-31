package com.ssafy.s103.domain.user.application.service;

import com.ssafy.s103.domain.user.application.repository.UserRepository;
import com.ssafy.s103.domain.user.dto.request.UserRegisterRequestDto;
import com.ssafy.s103.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDetails save(UserRegisterRequestDto userRegisterRequestDto) {
        return userRepository.save(User.builder()
            .email(userRegisterRequestDto.email())
            .password(bCryptPasswordEncoder.encode(userRegisterRequestDto.password()))
            .build());
    }

    public boolean login(String rawPassword, UserRegisterRequestDto userRegisterRequestDto) {
        User user = userRepository.findByEmail(userRegisterRequestDto.email())
            .orElseThrow(() -> new UsernameNotFoundException(userRegisterRequestDto.email()));
        if (user != null) {
            return passwordChecker(rawPassword, user.getPassword());
        } else {
            throw new UsernameNotFoundException(userRegisterRequestDto.email());
        }
    }

    public boolean passwordChecker(String rawPassword, String hashedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, hashedPassword);
    }

}