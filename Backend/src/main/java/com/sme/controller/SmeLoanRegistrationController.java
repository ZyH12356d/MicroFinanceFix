package com.sme.controller;

import com.sme.dto.SmeLoanRegistrationDTO;
import com.sme.entity.SmeLoanRegistration;
import com.sme.service.SmeLoanRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loans")
public class SmeLoanRegistrationController {

    @Autowired
    private SmeLoanRegistrationService service;

    @PostMapping("/register")
    public SmeLoanRegistration registerLoan(@RequestBody SmeLoanRegistrationDTO dto) {
        return service.registerLoan(dto);
    }

    @GetMapping
    public List<SmeLoanRegistration> getAllLoans() {
        return service.getAllLoans();
    }

    @GetMapping("/{id}")
    public Optional<SmeLoanRegistration> getLoanById(@PathVariable Long id) {
        return service.getLoanById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteLoan(@PathVariable Long id) {
        service.deleteLoan(id);
    }
}

