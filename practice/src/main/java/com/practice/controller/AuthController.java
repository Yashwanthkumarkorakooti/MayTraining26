package com.practice.controller;


import com.practice.dto.LoginResponseDto;
import com.practice.dto.RegisterReqDto;
import com.practice.dto.TokenDto;
import com.practice.model.User;
import com.practice.service.AuthService;
import com.practice.service.UserService;
import com.practice.util.JwtUtility;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtility jwtUtility;
    private final AuthService authService;
    /*
    .requestMatchers(HttpMethod.GET, "/api/auth/login").authenticated()

    This ensures that , if user request comes to this login() method at line
    19.. then Spring already has checked username/password and they are right
    and i can ask for username from spring
    * */

    @GetMapping("/login")
//    public LoginResponseDto login(Principal principal){  //spring is injecting principal interface object as Sping maintains it in its context
//        String loggedInUsername = principal.getName();
//        User user = (User)userService.loadUserByUsername(loggedInUsername);
//        return new LoginResponseDto(
//                user.getId(),
//                user.getUsername(),
//                user.getRole().toString()
//        );
//    }
    public TokenDto login(Principal principal){
        String username = principal.getName();
        String token = jwtUtility.generateToken(username);
        return new TokenDto(username,token);
    }

    // this is for later <----
    @GetMapping("/user-details")
    public LoginResponseDto getuserDetails(Principal principal){
        User user = (User)userService.loadUserByUsername(principal.getName());
        return new LoginResponseDto(
                user.getId(),
                user.getUsername(),
                user.getRole().toString()
        );
    }

    @PostMapping("register")
    public void userRegister(@Valid @RequestBody RegisterReqDto dto){
        authService.userRegister(dto);
    }


}


