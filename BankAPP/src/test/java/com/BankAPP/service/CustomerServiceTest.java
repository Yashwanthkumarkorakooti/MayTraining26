package com.BankAPP.service;

import com.BankAPP.dto.CustomerProfileDto;
import com.BankAPP.dto.CustomerReqDto;
import com.BankAPP.dto.UpdateKycReqDto;
import com.BankAPP.dto.UpdateKycRespDto;
import com.BankAPP.enums.Gender;
import com.BankAPP.enums.KycStatus;
import com.BankAPP.enums.Status;
import com.BankAPP.mapper.CustomerMapper;
import com.BankAPP.model.BankEmployee;
import com.BankAPP.model.Branch;
import com.BankAPP.model.Customer;
import com.BankAPP.model.User;
import com.BankAPP.respository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BankEmployeeRepository bankEmployeeRepository;
    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private User user;
    private BankEmployee employee;
    private Branch branch;

    @BeforeEach
    public void sampleData(){
        branch = new Branch();
        branch.setId(1);
        branch.setBranch_name("Maverick Branch");
        branch.setIfscCode("MAV0001");

        user = new User();
        user.setId(1);
        user.setUsername("yash");
        user.setEmail("yash@gmail.com");
        user.setPhone("9999999999");

        employee = new BankEmployee();
        employee.setId(1);
        employee.setFullName("Kumar");
        employee.setBranch(branch);

        customer = new Customer();
        customer.setId(1);
        customer.setUser(user);
        customer.setBranch(branch);
        customer.setFull_name("Yashwanth");
        customer.setPhone("9999999999");
        customer.setAadhaarNumber("123456789012");
        customer.setPanNumber("ABCDE1234F");
        customer.setCustomer_status(Status.ACTIVE);
        customer.setKyc_status(KycStatus.PENDING);

    }

    @Test
    public void getCustomers_mustReturnCustomers(){
        when(customerRepository.findAll()).thenReturn(List.of(customer));
        List<Customer> result = customerService.getCustomers();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getFull_name()).isEqualTo("Yashwanth");
        verify(customerRepository,times(1)).findAll();
    }

    @Test
    public void getCustomers_returnEmptyList(){
        when(customerRepository.findAll()).thenReturn(List.of());
        List<Customer> result = customerService.getCustomers();

        assertThat(result).isEmpty();
    }

    @Test
    public void getCustomerProfile_success(){
        when(userRepository.findByUsername("yash")).thenReturn(Optional.of(user));
        when(customerRepository.findByUserName("yash")).thenReturn(customer);

        CustomerProfileDto profileDto = new CustomerProfileDto(
                1,
                "Yashwanth",
                Instant.now(),
                Gender.MALE,
                "yash@gmail.com",
                "9999999999",
                "123456789012",
                "ABCDE1234F",
                "Andhra Pradesh",
                KycStatus.PENDING,
                Status.ACTIVE,
                "Maverick Branch",
                "MAV0001",
                "Kumar"
        );

        when(customerMapper.mapEntityToDto(customer)).thenReturn(profileDto);
        CustomerProfileDto result = customerService.getCustomerProfile("yash");
        assertThat(result.customerId()).isEqualTo(1);
        assertThat(result.fullName()).isEqualTo("Yashwanth");
    }

    @Test
    public void updateKycStatus_success(){
        customer.setPanNumber("ABCDE1234F");
        customer.setAadhaarNumber("123456789012");

        UpdateKycReqDto dto = new UpdateKycReqDto(KycStatus.VERIFIED);

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        UpdateKycRespDto result = customerService.updateKycStatus(1,dto,"Kumar");

        assertThat(result.customerId()).isEqualTo(1);
        assertThat(result.kycStatus()).isEqualTo(KycStatus.VERIFIED);

        verify(customerRepository,times(1)).save(customer);

    }


}
