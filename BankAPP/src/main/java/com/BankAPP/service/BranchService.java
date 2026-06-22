package com.BankAPP.service;

import com.BankAPP.dto.BranchDetailsRespDto;
import com.BankAPP.dto.CreateBranchReqDto;
import com.BankAPP.dto.CreateBranchRespDto;
import com.BankAPP.enums.Role;
import com.BankAPP.exception.ResourceNotFoundException;
import com.BankAPP.mapper.BranchMapper;
import com.BankAPP.model.Branch;
import com.BankAPP.model.User;
import com.BankAPP.respository.BranchRepository;
import com.BankAPP.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BranchService {

    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    public CreateBranchRespDto createBranch(String username, CreateBranchReqDto dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.ADMIN){
            throw new RuntimeException("Only admin can create branch");
        }

        boolean ifscExists = branchRepository.existsByIfscCode(dto.ifscCode());
        if (ifscExists){
            throw new RuntimeException("IFSC already exists");
        }

        boolean emailExists = branchRepository.existsByEmail(dto.email());
        if (emailExists){
            throw new RuntimeException("Email already exists");
        }

        boolean phoneExists = branchRepository.existsByPhone(dto.phone());

        if (phoneExists){
            throw new RuntimeException("Phone already exists");
        }


        Branch branch = new Branch();
        branch.setBranch_name(dto.branchName());
        branch.setIfscCode(dto.ifscCode());
        branch.setCity(dto.city());
        branch.setState(dto.state());
        branch.setEmail(dto.email());
        branch.setPhone(dto.phone());
        branch.setAddress(dto.address());
        branch.setPincode(dto.pincode());
        branch = branchRepository.save(branch);


        return branchMapper.mapCreateBranchDto(branch);
    }

    public BranchDetailsRespDto getBranchDetails(int branchId) {
        Branch branch = branchRepository.findById(branchId)
                        .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

        Integer employeesCount = branchRepository.getEmployeesCount(branchId);
        Integer customersCount = branchRepository.getCustomersCount(branchId);
        Integer accountsCount = branchRepository.getAccountsCount(branchId);

        Double loanPortfolio = branchRepository.getLoanPortfolio(branchId);

        if (employeesCount == null) employeesCount = 0;
        if (customersCount == null)  customersCount = 0;
        if (accountsCount == null) accountsCount = 0;
        if (loanPortfolio == null) loanPortfolio = 0.0;


        return branchMapper.mapBranchDetailsDto(branch, employeesCount,
                customersCount, accountsCount, loanPortfolio);
    }

    public List<Branch> getAllBranchDetails() {
        return branchRepository.findAll();
    }
}
