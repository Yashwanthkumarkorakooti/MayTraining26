package com.BankAPP.service;

import com.BankAPP.dto.*;
import com.BankAPP.enums.*;
import com.BankAPP.exception.ResourceNotFoundException;
import com.BankAPP.mapper.CustomerMapper;
import com.BankAPP.model.*;
import com.BankAPP.respository.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final BankEmployeeRepository bankEmployeeRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    private final CustomerAccountRepository customerAccountRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final LoanRepository loanRepository;

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public void createCustomer(@Valid CustomerReqDto customerReqDto, String employeeuserName) {
        // Aadhaar validation
        if (customerRepository.existsByAadhaarNumber(customerReqDto.aadhaarNumber())) {
            throw new RuntimeException("Aadhaar already exists");
        }
        // PAN validation
        if (customerRepository.existsByPanNumber(customerReqDto.panNumber())) {
            throw new RuntimeException("PAN already exists");
        }
        // Email validation
        if (userRepository.existsByEmail(customerReqDto.email())) {
            throw new RuntimeException("Email already exists"
            );
        }
        // Phone validation
        if (userRepository.existsByPhone(customerReqDto.phone())) {
            throw new RuntimeException("Phone already exists");
        }

        BankEmployee bankEmployee = bankEmployeeRepository.findByUsername(employeeuserName);

        User user = customerMapper.mapDtoToUser(customerReqDto);

        user.setPassword(passwordEncoder.encode("pass@123"));
        user.setRole(Role.CUSTOMER);
        user.setStatus(Status.ACTIVE);

        user = userRepository.save(user);

        Customer customer = customerMapper.mapDtoToCustomer(customerReqDto);
        customer.setUser(user);
        customer.setBranch(bankEmployee.getBranch());
        customer.setCreatedByEmployee(bankEmployee);
        customer.setAssigned_employee(bankEmployee);
        customer.setKyc_status(KycStatus.PENDING);
        customer.setCustomer_status(Status.ACTIVE);

        customerRepository.save(customer);
    }

    public CustomerProfileDto getCustomerProfile(String name) {
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Customer customer = customerRepository.findByUserName(user.getUsername());

        if(customer == null){
            throw new ResourceNotFoundException("Customer not found");
        }

        return customerMapper.mapEntityToDto(customer);
    }

    public List<CustomerAccountRespDto> getCustomerAccounts(String name) {
        Customer customer = customerRepository.findByUserName(name);
        if(customer == null){
            throw new ResourceNotFoundException(
                    "Customer Not Found"
            );
        }
        List<CustomerAccount> list = customerAccountRepository.getCustomerAccounts(customer.getId());

        return list.stream()
                .map(customerMapper::mapAccountToDto)
                .toList();
    }

    public List<CustomerSearchRespDto> searchCustomer(int page, int size, String name, String phone, String aadhaar, String pan, Integer branchId, Status status, KycStatus kycStatus) {
        Pageable pageable = PageRequest.of(page, size);

        List<Customer> list = customerRepository.searchCustomers(name,phone,aadhaar,pan,branchId,status,kycStatus,pageable).getContent();

        return list.stream()
                .map(customerMapper::mapCustomerSearchDto)
                .toList();
    }

    public UpdateKycRespDto updateKycStatus(int customerId, UpdateKycReqDto dto, String name) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Not Found"));

        BankEmployee bankEmployee = bankEmployeeRepository.findByUsername(name);

        if(dto.kycStatus() == KycStatus.VERIFIED){
            if(customer.getPanNumber() == null || customer.getAadhaarNumber() == null){
                throw new RuntimeException("Pan or Aadhar is missing");
            }
        }
        customer.setKyc_status(dto.kycStatus());

        customerRepository.save(customer);

        return new UpdateKycRespDto(
                customer.getId(),
                customer.getFull_name(),
                customer.getAadhaarNumber(),
                customer.getPanNumber(),
                customer.getKyc_status()
        );
        
     }

    public FinancialSummaryRespDto getFinancialSummary(String username) {
        Customer customer = customerRepository.findByUserName(username);

        if(customer == null)
            throw new ResourceNotFoundException("Customer Not Found");

        Double totalBalance = accountRepository.getTotalBalance(customer.getId());
        Double monthlySpending = transactionRepository.getMonthlySpending(customer.getId());
        Double loanOutStanding = loanRepository.getOustandingLoan(customer.getId());
        Double totalDeposits =  transactionRepository.getTotalDeposits(customer.getId());
        Double totalWithdrawals = transactionRepository.getTotalWithdrawals(customer.getId());

        return customerMapper.mapFinancialSummaryDto(totalBalance, monthlySpending,
                                                    loanOutStanding, totalDeposits, totalWithdrawals);

    }

    public List<MonthlySpendingRespDto> getMonthlySpending(String username) {
        Customer customer = customerRepository.findByUserName(username);

        List<Object[]> results = transactionRepository.getMonthlySpendings(customer.getId());

        List<MonthlySpendingRespDto> response = new ArrayList<>();

        String[] months = {"",
                "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
                "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"
        };

        for(Object[] row : results){
            int month = (Integer) row[0];
            Double amount = ((Number) row[1]).doubleValue();
            response.add(new MonthlySpendingRespDto(months[month], amount)
            );
        }

        return response;
    }

    public List<AccountDistributionRespDto> getAccountDistribution(String username) {
        Customer customer = customerRepository.findByUserName(username);
//        if(customer.getId() != (customerId)){
//            throw new RuntimeException("Unauthorized access");
//        }
        List<Object[]>results = accountRepository.getAccountDistribution(customer.getId());

        List<AccountDistributionRespDto> response = new ArrayList<>();
        for(Object[] row : results){
            Type type = (Type) row[0];
            Long count = ((Number) row[1]).longValue();
            response.add(new AccountDistributionRespDto(type.name(), count)
            );
        }
        return response;
    }

    public List<TransactionTypeAnalyticsRespDto> getTransactionTypeAnalytics(String username) {
        Customer customer = customerRepository.findByUserName(username);
//        if(customer.getId() != customerId){
//            throw new ResourceNotFoundException("Unauthorized access");
//        }
        List<Object[]> results = transactionRepository.getTransactionTypeAnalytics(customer.getId());

        List<TransactionTypeAnalyticsRespDto> response = new ArrayList<>();

        for(Object[] row : results){
            response.add(new TransactionTypeAnalyticsRespDto((TransactionType) row[0],
                    ((Number) row[1]).longValue())
            );
        }

        return response;
    }


}
