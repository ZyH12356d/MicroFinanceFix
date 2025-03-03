//package com.sme.controller;
//
//import com.sme.dto.CurrentAccountDTO;
//import com.sme.entity.CurrentAccount;
//import com.sme.service.CurrentAccountService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/current_account")
//public class CurrentAccountController {
//
//    @Autowired
//    private CurrentAccountService currentAccountService;
//
//    // Create a new current account
//    @PostMapping("/create")
//    public CurrentAccount createCurrentAccount(@RequestBody CurrentAccountDTO accountDTO) {
//        return currentAccountService.createCurrentAccount(accountDTO);
//    }
//
//    // Get all active current accounts
//    @GetMapping("/active")
//    public List<CurrentAccount> getAllActiveAccounts() {
//        return currentAccountService.getAllActiveAccounts();
//    }
//
//    // Get all inactive current accounts (soft deleted)
//    @GetMapping("/inactive")
//    public List<CurrentAccount> getAllInactiveAccounts() {
//        return currentAccountService.getAllInactiveAccounts();
//    }
//
//    // Get a specific current account by ID
//    @GetMapping("/{id}")
//    public Optional<CurrentAccount> getAccountById(@PathVariable Long id) {
//        return currentAccountService.getAccountById(id);
//    }
//
//    // Update an existing current account
//    @PutMapping("/update/{id}")
//    public CurrentAccount updateCurrentAccount(@PathVariable Long id, @RequestBody CurrentAccountDTO accountDTO) {
//        return currentAccountService.updateCurrentAccount(id, accountDTO);
//    }
//
//    // Soft delete (mark as inactive) a current account
//    @DeleteMapping("/delete/{id}")
//    public String softDeleteCurrentAccount(@PathVariable Long id) {
//        currentAccountService.softDeleteCurrentAccount(id);
//        return "Account soft deleted successfully!";
//    }
//}
