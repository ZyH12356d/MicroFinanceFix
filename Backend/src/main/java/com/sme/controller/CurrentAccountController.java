package com.sme.controller;

import com.sme.dto.CurrentAccountDTO;
import com.sme.service.CurrentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/current-accounts")
public class CurrentAccountController {

    @Autowired
    private CurrentAccountService currentAccountService;

    // Get all Current Accounts
    @GetMapping
    public List<CurrentAccountDTO> getAllCurrentAccounts() {
        return currentAccountService.getAllCurrentAccounts();
    }

    // Get Current Account by ID
    @GetMapping("/{id}")
    public ResponseEntity<CurrentAccountDTO> getCurrentAccountById(@PathVariable Long id) {
        Optional<CurrentAccountDTO> account = currentAccountService.getCurrentAccountById(id);
        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new Current Account
    @PostMapping
    public ResponseEntity<CurrentAccountDTO> createCurrentAccount(@RequestBody CurrentAccountDTO accountDTO) {
        return ResponseEntity.ok(currentAccountService.createCurrentAccount(accountDTO));
    }

    // Delete Current Account
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurrentAccount(@PathVariable Long id) {
        currentAccountService.deleteCurrentAccount(id);
        return ResponseEntity.noContent().build();
    }
}
