package com.sme.controller;

import com.sme.dto.SmeLoanRegistrationDTO;
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

    @PostMapping
    public ResponseEntity<SmeLoanRegistrationDTO> createLoan(@RequestBody SmeLoanRegistrationDTO dto) {
        return ResponseEntity.ok(loanService.createLoan(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SmeLoanRegistrationDTO> getLoanById(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoanById(id));
    }
}
