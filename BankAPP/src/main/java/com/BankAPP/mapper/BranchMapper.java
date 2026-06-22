package com.BankAPP.mapper;

import com.BankAPP.dto.BranchDetailsRespDto;
import com.BankAPP.dto.CreateBranchRespDto;
import com.BankAPP.model.Branch;
import org.springframework.stereotype.Component;

@Component
public class BranchMapper {
    public CreateBranchRespDto mapCreateBranchDto(Branch branch) {
        return new CreateBranchRespDto(
                branch.getId(),
                branch.getBranch_name(),
                branch.getIfscCode(),
                branch.getCity(),
                branch.getState(),
                branch.getEmail(),
                branch.getPhone(),
                branch.getCreated_at(),
                "Branch created successfully"
        );
    }

    public BranchDetailsRespDto mapBranchDetailsDto(Branch branch, Integer employeesCount, Integer customersCount, Integer accountsCount, Double loanPortfolio) {
        return new BranchDetailsRespDto(
                branch.getId(),
                branch.getBranch_name(),
                branch.getIfscCode(),
                branch.getCity(),
                branch.getState(),
                branch.getEmail(),
                branch.getPhone(),
                employeesCount,
                customersCount,
                accountsCount,
                loanPortfolio
        );
    }
}
