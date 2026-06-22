package com.BankAPP.controller;



import com.BankAPP.dto.LoginResponseDto;
import com.BankAPP.dto.SignupReqDto;
import com.BankAPP.dto.TokenDto;
import com.BankAPP.model.User;
import com.BankAPP.service.AuthService;
import com.BankAPP.service.UserService;
import com.BankAPP.util.JwtUtility;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    private final JwtUtility jwtUtility;
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
//    @GetMapping("/user-details")
//    public LoginResponseDto getuserDetails(Principal principal){
//        String token = authHeader.substring(7); // remove Bearer
//
//        String username = jwtUtility.extractUsername(token);
//        User user = (User)userService.loadUserByUsername(principal.getName());
//        return new LoginResponseDto(
//                user.getId(),
//                user.getUsername(),
//                user.getRole().toString()
//        );
//    }
    @GetMapping("/user-details")
    public LoginResponseDto getuserDetails(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7); // remove "Bearer "
        String username = jwtUtility.extractUsername(token);
        User user = (User) userService.loadUserByUsername(username);
        return new LoginResponseDto(
                user.getId(),
                user.getUsername(),
                user.getRole().toString()
        );
    }

    @PostMapping("/signup")
    public void signup(@Valid @RequestBody SignupReqDto dto){
        authService.signup(dto);
    }

}

