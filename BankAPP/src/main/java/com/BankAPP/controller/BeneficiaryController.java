package com.BankAPP.controller;

import com.BankAPP.dto.BeneficiaryReqDto;
import com.BankAPP.dto.BeneficiaryRespDto;
import com.BankAPP.dto.BlockBeneficiaryRespDto;
import com.BankAPP.dto.GetBeneficiaryRespDto;
import com.BankAPP.service.BeneficiaryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class BeneficiaryController {

    private final BeneficiaryService beneficiaryService;

    @PostMapping("/api/v1/customers/beneficiaries")
    public BeneficiaryRespDto addBeneficiary(Principal principal,
                                             @RequestBody BeneficiaryReqDto dto){
        String userName = principal.getName();
        return beneficiaryService.addBeneficiary(userName,dto);
    }

    @PutMapping("/api/v1/beneficiaries/{beneficiaryId}/block")
    public BlockBeneficiaryRespDto blockBeneficiary(Principal principal,
                                        @PathVariable int beneficiaryId){

        String username = principal.getName();
        return beneficiaryService.blockBeneficiary(username, beneficiaryId);
    }

    @GetMapping("/api/v1/customer/beneficiaries")
    public List<GetBeneficiaryRespDto> getBeneficiaries(Principal principal){
        String username = principal.getName();
        return beneficiaryService.getBeneficiaries(username);
    }
}
