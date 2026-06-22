package com.BankAPP.service;

import com.BankAPP.dto.*;
import com.BankAPP.enums.*;
import com.BankAPP.exception.ResourceNotFoundException;
import com.BankAPP.model.*;
import com.BankAPP.respository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanRepaymentRepository loanRepaymentRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BankEmployeeRepository bankEmployeeRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AdminService adminService;

    private Admin admin;
    private Customer customer;
    private BankEmployee employee;
    private Branch branch;
    private User user;

    @BeforeEach
    public void sampleData() {

        branch = new Branch();
        branch.setId(1);
        branch.setBranch_name("Maverick Branch");

        user = new User();
        user.setId(1);
        user.setUsername("admin");
        user.setStatus(Status.ACTIVE);

        admin = new Admin();
        admin.setId(1);
        admin.setName("Super Admin");
        admin.setUser(user);

        employee = new BankEmployee();
        employee.setId(1);
        employee.setBranch(branch);
        employee.setUser(user);

        customer = new Customer();
        customer.setId(1);
        customer.setBranch(branch);
    }

    @Test
    public void getBranchPerformance_success(){
        Object[] row = {
                "Maverick Branch", 50000.0, 100L, 80L, 25L
        };

        when(adminRepository.findByUsername("admin")).thenReturn(admin);
        when(branchRepository.getBranchPerformance()).thenReturn(List.<Object[]>of(row));

        List<BranchPerformanceRespDto> result = adminService.getBranchPerformance("admin");
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().branchName()).isEqualTo("Maverick Branch");
    }

    @Test
    public void getBranchPerformance_unauthorized() {
        when(adminRepository.findByUsername("admin")).thenReturn(null);

        assertThatThrownBy(() ->
                adminService.getBranchPerformance("admin"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Unauthorized access");
    }

    @Test
    public void assignCustomerToEmployee_success(){
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(bankEmployeeRepository.findById(1)).thenReturn(Optional.of(employee));

        String result = adminService.assignCustomerToEmployee(1,1);
        assertThat(result).isEqualTo("Customer assigned successfully");

        verify(customerRepository,times(1)).save(customer);
    }

    @Test
    public void assignCustomerToEmployee_differentBranch() {
        Branch anotherBranch = new Branch();
        anotherBranch.setId(99);

        employee.setBranch(anotherBranch);

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(bankEmployeeRepository.findById(1)).thenReturn(Optional.of(employee));

        assertThatThrownBy(() ->
                adminService.assignCustomerToEmployee(1,1))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Customer and Employee must belong to same branch");
    }

    @Test
    public void delete_adminExists() {
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));

        doNothing().when(adminRepository).deleteById(1);

        adminService.delete(1);

        verify(adminRepository,times(1)).findById(1);
        verify(adminRepository,times(1)).deleteById(1);
    }

    @Test
    public void delete_adminNotFound() {
        when(adminRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                adminService.delete(1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Admin Not Found");

        verify(adminRepository,times(1)).findById(1);
        verify(adminRepository,never()).deleteById(1);
    }

}