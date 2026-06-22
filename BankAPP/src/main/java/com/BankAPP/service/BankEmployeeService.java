package com.BankAPP.service;

import com.BankAPP.dto.*;
import com.BankAPP.enums.EmployeeDesignation;
import com.BankAPP.enums.Role;
import com.BankAPP.enums.Status;
import com.BankAPP.exception.ResourceNotFoundException;
import com.BankAPP.mapper.AccountMapper;
import com.BankAPP.mapper.EmployeeMapper;
import com.BankAPP.model.*;
import com.BankAPP.respository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BankEmployeeService {
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;
    private final BankEmployeeRepository bankEmployeeRepository;
    private final EmployeeMapper employeeMapper;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final LoanRepository loanRepository;
    private final TransactionRepository transactionRepository;
    private final AccountMapper accountMapper;

    public List<BankEmployee> getEmployees() {
        return bankEmployeeRepository.findAll();
    }

    public CreateEmployeeRespDto createEmployee(CreateEmployeeReqDto dto, String name) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.existsByPhone(dto.phone())) {
            throw new RuntimeException("Phone already exists");
        }

        Branch branch = branchRepository.findById(dto.branchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setEmail(dto.email());

        user.setPhone(dto.phone());
        user.setRole(Role.EMPLOYEE);
        user.setStatus(Status.ACTIVE);
        user = userRepository.save(user);


        BankEmployee employee = new BankEmployee();
        employee.setUser(user);
        employee.setBranch(branch);
        employee.setFullName(dto.fullName());
        employee.setDesignation(dto.designation());
        employee.setSalary(dto.salary());
        employee.setStatus(Status.ACTIVE);
        employee.setEmployeeCode("EMP" + System.currentTimeMillis());

        employee = bankEmployeeRepository.save(employee);

        return employeeMapper.mapEmployeeDto(employee);
    }

    public EmployeeDetailsRespDto getEmployeeDetails(String name) {

        BankEmployee employee = bankEmployeeRepository.findByUsername(name);
        if (employee == null) {
            throw new ResourceNotFoundException("Employee Not Found");
        }

        Long customersHandled = customerRepository.countByCreatedByEmployeeId(employee.getId());

        Long accountsApproved = accountRepository.countApprovedAccounts(employee.getId());

        Long loansReviewed = loanRepository.countReviewedLoans(employee.getId());

        return employeeMapper
                .mapEmployeeDetailsDto(
                        employee,
                        customersHandled,
                        accountsApproved,
                        loansReviewed
                );
    }

    public TransferEmployeeRespDto transferEmployee(int employeeId, TransferEmployeeReqDto dto, String name) {
        BankEmployee employee = bankEmployeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (employee.getStatus() != Status.ACTIVE) {
            throw new RuntimeException("Inactive employee cannot transfer");
        }

        Branch newBranch = branchRepository.findById(dto.newBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        if (employee.getBranch().getId() == newBranch.getId()) {
            throw new RuntimeException(employee.getFullName()
                            + " is already assigned to " + newBranch.getBranch_name()
                            + " assigned to different branch."
            );
        }

        String oldBranch = employee.getBranch().getBranch_name();
        employee.setBranch(newBranch);

        employee = bankEmployeeRepository.save(employee);


        return employeeMapper.mapTransferEmployeeDto(employee, oldBranch);
    }

    public List<EmployeeRespDto> getAllEmployees(EmployeeDesignation designation, Integer branchId,
                                                 Status status, Double minSalary, Double maxSalary, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<BankEmployee> employees = bankEmployeeRepository.getAllEmployees(
                designation, branchId, status,
                minSalary, maxSalary, pageable).getContent();

        return employees
                .stream()
                .map(employeeMapper::mapEmployeeRespDto)
                .toList();
    }

    public EmployeeTransactionAnalyticsRespDto getTransactionAnalytics(String username) {
        BankEmployee employee = bankEmployeeRepository.findByUsername(username);


        Double deposits = transactionRepository.getTotalDeposit();
        Double withdrawals = transactionRepository.getTotalWithdrawal();
        Double transfers = transactionRepository.getTotalTransfers();


        deposits = deposits == null ? 0.0 : deposits;
        withdrawals = withdrawals == null ? 0.0 : withdrawals;
        transfers = transfers == null ? 0.0 : transfers;

        return new EmployeeTransactionAnalyticsRespDto(deposits, withdrawals, transfers);
    }

    public List<EmployeeLoanAnalyticsRespDto> getLoanAnalytics(String username) {
        BankEmployee employee = bankEmployeeRepository.findByUsername(username);
//        if(employee.getId() != employeeId){
//            throw new RuntimeException("Unauthorized access");
//        }
        List<Object[]> results = loanRepository.getLoanAnalytics(employee.getId());
        List<EmployeeLoanAnalyticsRespDto> response = new ArrayList<>();

        for (Object[] row : results) {
            response.add(new EmployeeLoanAnalyticsRespDto(
                    row[0].toString(), ((Number) row[1]).longValue())
            );
        }

        return response;
    }

    public List<PendingLoanRespDto> getPendingLoans(String username) {
        BankEmployee employee = bankEmployeeRepository.findByUsername(username);

        if (employee == null) {
            throw new RuntimeException("Employee not found");
        }

        List<Loan> loans = loanRepository.getPendingLoans();

        List<PendingLoanRespDto> response = new ArrayList<>();

        for (Loan loan : loans) {

            response.add(new PendingLoanRespDto(
                            loan.getId(), loan.getCustomer().getFull_name(),
                            loan.getLoan_type(), loan.getLoan_amount(),
                            loan.getLoan_term_months(),
                            loan.getLoan_status()
                    )
            );
        }

        return response;
    }

    public List<PendingAccountRespDto> getPendingAccounts(String username) {
        BankEmployee employee = bankEmployeeRepository.findByUsername(username);
        if (employee == null) {
            throw new RuntimeException("Employee not found");
        }
        List<Account> accounts = accountRepository.getPendingAccounts();

        List<PendingAccountRespDto> response = new ArrayList<>();

        for (Account account : accounts) {
            response.add(new PendingAccountRespDto(
                            account.getId(),
                            account.getCustomer().getFull_name(),
                            account.getAccountNumber(),
                            account.getType().toString(),
                            account.getBalance(),
                            account.getAccountStatus()
                    )
            );
        }

        return response;
    }

    public List<EmployeeCustomerRespDto> getAssignedCustomers(String username, String name, String phone,
            String aadhaar, String pan, String filter, int page, int size) {

        BankEmployee employee = bankEmployeeRepository.findByUsername(username);
        Pageable pageable = PageRequest.of(page, size);
        List<Customer> customers = customerRepository.getAssignedCustomer(
                        employee.getId(), name, phone, aadhaar, pan, pageable).getContent();

        return customers.stream().map(customer -> {
            Integer accounts = accountRepository.getActiveAccounts(customer.getId());
            Integer loans = loanRepository.getActiveLoans(customer.getId());
            Double balance = accountRepository.getCustomerBalance(customer.getId());
//            Account account = accountRepository.getAccounts(employee.getId());
            accounts = accounts == null ? 0 : accounts;
            loans = loans == null ? 0 : loans;
            balance = balance == null ? 0.0 : balance;
            String riskLevel = loans > 2 ? "HIGH" : "LOW";

            return employeeMapper.mapEmployeeCustomerDto(
                                    customer, accounts, loans, balance, riskLevel
                            );

                })
                .toList();
    }

    public List<AssignedLoanRespDto> getAssignedLoans(
            String username, String customerName, String phone,
            String loanType, String status, int page, int size
    ) {

        BankEmployee employee = bankEmployeeRepository.findByUsername(username);
        Pageable pageable = PageRequest.of(page, size);
        List<Loan> loans = loanRepository.getAssignedLoans(
                        employee.getId(), customerName, phone,
                        loanType, status, pageable).getContent();

        return loans.stream()
                .map(loan -> {
                    Integer customerId = loan.getCustomer().getId();
                    Double avgDeposit = transactionRepository.getAvgDeposit(customerId);
                    Double avgWithdrawal = transactionRepository.getAvgWithdrawal(customerId);
                    avgDeposit = avgDeposit == null ? 0.0 : avgDeposit;
                    avgWithdrawal = avgWithdrawal == null ? 0.0 : avgWithdrawal;
                    String riskLevel = avgWithdrawal > avgDeposit ? "HIGH" : "LOW";
                    return employeeMapper.mapAssignedLoanDto(
                                    loan,
                                    avgDeposit,
                                    avgWithdrawal,
                                    riskLevel
                            );

                })
                .toList();
    }

    public List<AssignedAccountRespDto> getAssignedAccounts(
            String username, String customerName, String phone, String accountType,
            String status, int page, int size
    ) {

        BankEmployee employee =
                bankEmployeeRepository.findByUsername(username);

        Pageable pageable =
                PageRequest.of(page, size);

        List<Account> accounts =
                accountRepository.getAssignedAccounts(
                        employee.getId(),
                        customerName,
                        phone,
                        accountType,
                        status,
                        pageable
                ).getContent();

        return accounts.stream()
                .map(accountMapper::mapAssignedAccountDto)
                .toList();
    }


}
