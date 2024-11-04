package com.ssafy.s103.domain.user.application.service;

import com.ssafy.s103.domain.user.application.repository.UserRepository;
import com.ssafy.s103.domain.user.dto.request.UserRegisterRequestDto;
import com.ssafy.s103.domain.user.entity.User;
import com.ssafy.s103.domain.user.exception.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(UserRegisterRequestDto userRegisterRequestDto) {
        String email = userRegisterRequestDto.email();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        userRepository.save(User.builder()
            .email(email)
            .password(bCryptPasswordEncoder.encode(userRegisterRequestDto.password()))
            .build());
    }

//    public boolean login(String rawPassword, UserLoginRequestDto userLoginRequestDto) {
//        User user = userRepository.findByEmail(userLoginRequestDto.email())
//            .orElseThrow(() -> new UsernameNotFoundException(userLoginRequestDto.email()));
//        return passwordChecker(rawPassword, user.getPassword());
//    }
//
//    public boolean passwordChecker(String rawPassword, String hashedPassword) {
//        return bCryptPasswordEncoder.matches(rawPassword, hashedPassword);
//    }

}