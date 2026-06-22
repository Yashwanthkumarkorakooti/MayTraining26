package com.BankAPP.service;

import com.BankAPP.dto.SignupReqDto;
import com.BankAPP.enums.Role;
import com.BankAPP.enums.Status;
import com.BankAPP.model.User;
import com.BankAPP.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void signup(SignupReqDto dto) {
        if (userRepository.existsByUsername(dto.username())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.existsByPhone(dto.phone())) {
            throw new RuntimeException("Phone already exists");
        }


        User user = new User();

        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setEmail(dto.email());
        user.setPhone(dto.phone());

        user.setRole(Role.CUSTOMER);
        user.setStatus(Status.ACTIVE);

        userRepository.save(user);
    }
}
