package com.sme.controller;

import com.sme.dto.SmeLoanRegistrationDTO;
import com.sme.entity.SmeLoanRegistration;
import com.sme.service.SmeLoanRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class SmeLoanRegistrationController {

    @Autowired
    private SmeLoanRegistrationService loanService;
    //private final SmeLoanRegistrationService loanService;

    @PostMapping("/register")
    public ResponseEntity<?> registerLoan(@RequestBody SmeLoanRegistration smeLoan) {
        //System.out.println("Collateral Value: " + collateralValue);
//        System.out.println("Collateral Amount: " + collateralAmount);
        System.out.println("Loan Amount: " + smeLoan.getLoanAmount());
        try {
            SmeLoanRegistration savedLoan = loanService.registerLoan(smeLoan);
            return ResponseEntity.ok(savedLoan);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SmeLoanRegistrationDTO> getLoanById(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoanById(id));
    }


}
