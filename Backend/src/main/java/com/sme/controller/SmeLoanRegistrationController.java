package com.sme.controller;

import com.sme.dto.SmeLoanRegistrationDTO;
import com.sme.entity.SmeLoanRegistration;
import com.sme.service.SmeLoanRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class SmeLoanRegistrationController {

    private final SmeLoanRegistrationService loanService;

    @PostMapping("/register")
    public ResponseEntity<?> registerLoan(@RequestBody SmeLoanRegistration smeLoan) {
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
