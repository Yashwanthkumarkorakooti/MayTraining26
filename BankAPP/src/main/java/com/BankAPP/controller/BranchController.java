package com.BankAPP.controller;

import com.BankAPP.dto.BranchDetailsRespDto;
import com.BankAPP.dto.CreateBranchReqDto;
import com.BankAPP.dto.CreateBranchRespDto;
import com.BankAPP.model.Branch;
import com.BankAPP.service.BranchService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class BranchController {

    private final BranchService branchService;

    @PostMapping("/api/v1/admin/branches")
    public CreateBranchRespDto createBranch(Principal principal, @RequestBody CreateBranchReqDto dto){
        String username = principal.getName();
        return branchService.createBranch(username, dto);
    }

    @GetMapping("/api/v1/branches")
    public List<Branch> getAllBranchDetails(){
        return branchService.getAllBranchDetails();
    }

}
