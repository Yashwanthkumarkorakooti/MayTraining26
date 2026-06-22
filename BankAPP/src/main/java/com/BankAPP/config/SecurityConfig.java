package com.BankAPP.config;


import com.BankAPP.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final JwtFilter jwtFilter;
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
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()   // SignUp
                        .requestMatchers(HttpMethod.GET,"/api/auth/login").authenticated()    // Login

                        .requestMatchers(HttpMethod.GET, "/api/v1/accounts/*").authenticated()      // all account Details
                        .requestMatchers(HttpMethod.GET, "/api/v1/accounts").authenticated()      // all account
                        .requestMatchers(HttpMethod.GET, "/api/v1/branches").authenticated()       // Get Branch
                        .requestMatchers(HttpMethod.POST, "/api/v1/employes").authenticated()       // Get Employees
                        .requestMatchers(HttpMethod.POST, "/api/v1/customers").authenticated()      // Get Customers
                        .requestMatchers(HttpMethod.POST, "/api/v1/admin/pending-loans").hasAuthority("ADMIN")      // Get Customers


                        .requestMatchers(HttpMethod.POST, "/api/v1/employees/customers").hasAuthority("EMPLOYEE")  // Employee Adds Customer
                        .requestMatchers(HttpMethod.PUT, "/api/v1/customer/accounts/approve/*").hasAuthority("EMPLOYEE")  // approve account
                        .requestMatchers(HttpMethod.PUT, "/api/v1/customer/accounts/reject/*").hasAuthority("EMPLOYEE")   // reject account
                        .requestMatchers(HttpMethod.PUT, "/api/v1/employees/accounts/freeze/*").hasAuthority("EMPLOYEE")     // Freeze Account
                        .requestMatchers(HttpMethod.GET, "/api/v1/customers/search").hasAuthority("EMPLOYEE")         // search Customers
                        .requestMatchers(HttpMethod.PUT, "/api/v1/employee/kyc-update/*").hasAuthority("EMPLOYEE")   // KYC update
                        .requestMatchers(HttpMethod.GET, "/api/v1/employees/loans/review/*").hasAuthority("EMPLOYEE")    // Loan review
                        .requestMatchers(HttpMethod.PUT, "/api/v1/employees/loans/approve/*").hasAuthority("EMPLOYEE")   // approve loan
                        .requestMatchers(HttpMethod.PUT, "/api/v1/employees/loans/reject/*").hasAuthority("EMPLOYEE")     // reject loan
                        .requestMatchers(HttpMethod.GET, "/api/v1/employees").hasAuthority("EMPLOYEE")         // Get Employee Details
                        .requestMatchers(HttpMethod.POST, "/api/v1/employees/loans/*/disburse").hasAuthority("EMPLOYEE")  // Disburse Loan
                        .requestMatchers(HttpMethod.POST, "/api/v1/accounts/joint-holder/*").hasAuthority("EMPLOYEE")             // Add Joint Account
                        .requestMatchers(HttpMethod.GET, "/api/v1/employees/analytics/transactions").hasAuthority("EMPLOYEE") // Employee Transaction Analytics  (Bar Chart)
                        .requestMatchers(HttpMethod.GET, "/api/v1/employees/analytics/loan-status").hasAuthority("EMPLOYEE")   // Employee Loan Analytics  (Pie Chart)
                        .requestMatchers(HttpMethod.GET, "/api/v1/employees/loans/pending").hasAuthority("EMPLOYEE")    // Pending loans
                        .requestMatchers(HttpMethod.GET, "/api/v1/employees/accounts/pending").hasAuthority("EMPLOYEE")   // Pending Accounts

                        .requestMatchers(HttpMethod.GET, "/api/v1/employees/assign-customers").hasAuthority("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/v1/employees/assigned-loans").hasAuthority("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/v1/employees/assigned-accounts").hasAuthority("EMPLOYEE")



                        .requestMatchers(HttpMethod.POST, "/api/v1/reports/account-statement").hasAnyAuthority("EMPLOYEE","CUSTOMER")    // Account statements
                        .requestMatchers(HttpMethod.GET, "/api/v1/customer-accounts/accounts/*").hasAnyAuthority("CUSTOMER","EMPLOYEE")  // Customer Accounts
                        .requestMatchers(HttpMethod.POST, "/api/v1/reports/financial/*").hasAnyAuthority("ADMIN", "EMPLOYEE")  // financial Report



                        .requestMatchers(HttpMethod.GET, "/api/v1/customer-details").hasAnyAuthority("CUSTOMER" , "EMPLOYEE")     // Get Customer Details
                        .requestMatchers(HttpMethod.POST, "/api/v1/customer/accounts/request").hasAuthority("CUSTOMER")  // request for open an Account
                        .requestMatchers(HttpMethod.POST, "/api/v1/customer/accounts/close-request/*").hasAuthority("CUSTOMER") // close account
                        .requestMatchers(HttpMethod.POST, "/api/v1/accounts/employee/deposit").hasAuthority("CUSTOMER")   // Deposit amount
                        .requestMatchers(HttpMethod.POST, "/api/v1/accounts/employee/withdraw").hasAuthority("CUSTOMER")   // withdraw amount
                        .requestMatchers(HttpMethod.POST, "/api/v1/transactions/transfer").hasAuthority("CUSTOMER")         // transfer amount from a to b
                        .requestMatchers(HttpMethod.GET, "/api/v1/customers/transactions").hasAuthority("CUSTOMER")       // get All transactions
                        .requestMatchers(HttpMethod.POST, "/api/v1/customers/loans/apply").hasAuthority("CUSTOMER")   // apply Loan
                        .requestMatchers(HttpMethod.GET, "/api/v1/customer/financial-summary").hasAuthority("CUSTOMER")  // Customer Financial Summary
                        .requestMatchers(HttpMethod.POST, "/api/v1/customers/beneficiaries").hasAuthority("CUSTOMER")  // Add beneficiary
                        .requestMatchers(HttpMethod.PUT, "/api/v1/beneficiaries/*/block").hasAuthority("CUSTOMER")      // Block Beneficiary
                        .requestMatchers(HttpMethod.GET, "/api/v1/customer/beneficiaries").hasAuthority("CUSTOMER")  // Get All beneficiaries
                        .requestMatchers(HttpMethod.GET, "/api/v1/customers/loans").hasAuthority("CUSTOMER")          // Customer Loans
                        .requestMatchers(HttpMethod.GET, "/api/v1/loans/*").hasAuthority("CUSTOMER")     // Get Loan Details
                        .requestMatchers(HttpMethod.POST, "/api/v1/loans/*/pay-emi").hasAuthority("CUSTOMER")  // Pay EMI
                        .requestMatchers(HttpMethod.GET, "/api/v1/loans/*/repayments").hasAuthority("CUSTOMER")  // Repayment history
                        .requestMatchers(HttpMethod.GET, "/api/v1/customers/loan-eligibility").hasAuthority("CUSTOMER")   // Loan Eligibility
                        .requestMatchers(HttpMethod.GET, "/api/v1/customers/*/analytics/monthly-spending").hasAuthority("CUSTOMER") // Customer Spending Analytics (Bar Chart)
                        .requestMatchers(HttpMethod.GET, "/api/v1/customers/*/analytics/account-distribution").hasAuthority("CUSTOMER")  // Customer Account Distribution (Pie Chart)
                        .requestMatchers(HttpMethod.GET, "/api/v1/customers/*/analytics/transaction-types").hasAuthority("CUSTOMER")   // Customer Transaction Type Analytics (Pie Chart)
                        .requestMatchers(HttpMethod.GET,"/api/v1/customers/accounts").hasAuthority("CUSTOMER")  // ALL accounts


                        .requestMatchers(HttpMethod.POST, "/api/v1/admin/employees").hasAuthority("ADMIN")       // Admin add Employee
                        .requestMatchers(HttpMethod.PUT, "/api/v1/admin/employees/transfer/*").hasAuthority("ADMIN")  // Transfer employee
                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/employees").hasAuthority("ADMIN")           // Get All Employees
                        .requestMatchers(HttpMethod.POST, "/api/v1/admin/branches").hasAuthority("ADMIN")     // create Branch
                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/analytics/branch-performance").hasAuthority("ADMIN")   // Admin Branch Performance Analytics (Bar Chart)
                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/analytics/transactions").hasAuthority("ADMIN")    // Admin Transaction Analytics (Line Chart)
                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/analytics/loan-portfolio").hasAuthority("ADMIN")   // Admin Loan Portfolio Analytics (Pie Chart)
                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/analytics/revenue").hasAuthority("ADMIN")       // Revenue Analytics API (Bar Chart)

                        .requestMatchers(HttpMethod.PUT, "/api/v1/admin/customers/*/assign-employee/*").hasAuthority("ADMIN")  // assign employee to customer
                        .requestMatchers(HttpMethod.PUT, "/api/v1/admin/loans/*/assign/*").hasAuthority("ADMIN")   //  assign loan
                        .requestMatchers(HttpMethod.PUT, "/api/v1/admin/accounts/*/assign/*").hasAuthority("ADMIN")  // assign Account
                        .requestMatchers(HttpMethod.PUT, "/api/v1/admin/customers/*/assign-kyc/*").hasAuthority("ADMIN") // kyc updation

                        .anyRequest().permitAll()
                )

                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtFilter,
                UsernamePasswordAuthenticationFilter.class
        );
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
