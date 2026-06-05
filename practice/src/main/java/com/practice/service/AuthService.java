package com.practice.service;

import com.practice.dto.RegisterReqDto;
import com.practice.model.User;
import com.practice.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public void userRegister(@Valid RegisterReqDto dto) {
        if(userRepository.existsByUsername(dto.username())){
            throw new RuntimeException("User Already Found");
        }
        User user = new User();

        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(dto.role());

        userRepository.save(user);
    }
}
