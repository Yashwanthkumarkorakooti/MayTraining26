package com.practice.config;


import com.practice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final UserService userService;
//    @Bean
//    public UserDetailsService users() {
//        UserDetails user1 = User.builder()
//                .username("harry")
//                .password("{noop}harry@123")
//                .authorities("CUSTOMER")
//                .build();
//        UserDetails user2 = User.builder()
//                .username("ronald")
//                .password("{noop}ronald@123")
//                .authorities("EXECUTIVE")
//                .build();
//        return new InMemoryUserDetailsManager(user1, user2);
//    }

    @Bean
    public SecurityFilterChain approvalsSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST,"/api/auth/register").permitAll()  // user SignUp
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").authenticated()  // user Login
                        .requestMatchers(HttpMethod.GET,"/api/all/jobs").hasAnyRole("EMPLOYER","SEEKER")  // All Jobs
                        .requestMatchers(HttpMethod.POST,"/api/jobs").hasRole("EMPLOYER")  // Post Job
                        .requestMatchers(HttpMethod.POST,"/api/applications/*").hasRole("SEEKER")  // Apply for job
                        .requestMatchers(HttpMethod.GET,"/api/my-applications").hasRole("SEEKER")  // get my applications
                        .anyRequest().permitAll()
                )

                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

