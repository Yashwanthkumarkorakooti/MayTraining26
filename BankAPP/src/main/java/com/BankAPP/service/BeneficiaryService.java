package com.BankAPP.service;

import com.BankAPP.dto.BeneficiaryReqDto;
import com.BankAPP.dto.BeneficiaryRespDto;
import com.BankAPP.dto.BlockBeneficiaryRespDto;
import com.BankAPP.dto.GetBeneficiaryRespDto;
import com.BankAPP.enums.Status;
import com.BankAPP.exception.ResourceNotFoundException;
import com.BankAPP.mapper.BeneficiaryMapper;
import com.BankAPP.model.Beneficiary;
import com.BankAPP.model.Branch;
import com.BankAPP.model.Customer;
import com.BankAPP.respository.AccountRepository;
import com.BankAPP.respository.BeneficiaryRepository;
import com.BankAPP.respository.BranchRepository;
import com.BankAPP.respository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BeneficiaryService {
    private final CustomerRepository customerRepository;
    private final BranchRepository branchRepository;
    private final AccountRepository accountRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    private final BeneficiaryMapper beneficiaryMapper;


    public BeneficiaryRespDto addBeneficiary(String userName, BeneficiaryReqDto dto) {
        Customer customer = customerRepository.findByUserName(userName);

        Branch branch = branchRepository.findByIfscCode(dto.ifscCode());
        if (branch == null) {
            throw new RuntimeException("Invalid IFSC");
        }

        boolean ownAccountExists = accountRepository.existsOwnAccount(customer.getId(), dto.accountNumber());
        if (ownAccountExists) {
            throw new RuntimeException("Cannot add own account");
        }

        boolean accountExists = beneficiaryRepository.existsByCustomerIdAndAccountNumber(customer.getId(), dto.accountNumber());
        if (accountExists) {
            throw new RuntimeException("Beneficiary already exists");
        }

        boolean nicknameExists = beneficiaryRepository.existsByCustomerIdAndNickname(customer.getId(), dto.nickname());
        if (nicknameExists) {
            throw new RuntimeException("Nickname already exists");
        }


        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setCustomer(customer);
        beneficiary.setBeneficiaryName(dto.beneficiaryName());
        beneficiary.setBankName(dto.bankName());
        beneficiary.setIfscCode(dto.ifscCode());
        beneficiary.setAccountNumber(dto.accountNumber());
        beneficiary.setNickname(dto.nickname());
        beneficiary.setStatus(Status.ACTIVE);
        beneficiary = beneficiaryRepository.save(beneficiary);


        return beneficiaryMapper.mapBeneficiaryDto(beneficiary);

    }

    public BlockBeneficiaryRespDto blockBeneficiary(String username, int beneficiaryId) {
        Customer customer = customerRepository.findByUserName(username);
        Beneficiary beneficiary = beneficiaryRepository.findById(beneficiaryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Beneficiary not found"));


        if (beneficiary.getCustomer().getId() != customer.getId()) {
            throw new RuntimeException("Unauthorized access");
        }

        if (beneficiary.getStatus() != Status.ACTIVE) {
            throw new RuntimeException("Beneficiary account inactive");
        }

        String oldStatus = beneficiary.getStatus().name();

        beneficiary.setStatus(Status.BLOCKED);
        beneficiary = beneficiaryRepository.save(beneficiary);

        return beneficiaryMapper.mapBlockBeneficiaryDto(beneficiary, oldStatus);

    }

    public List<GetBeneficiaryRespDto> getBeneficiaries(String username) {
        Customer customer = customerRepository.findByUserName(username);


        List<Beneficiary> beneficiaries = beneficiaryRepository.getBeneficiaries(customer.getId());

        return beneficiaries
                .stream()
                .map(beneficiaryMapper:: mapGetBeneficiaryDto)
                .toList();
    }
}
