package com.sme.controller;

import com.sme.entity.AccountTransaction;
import com.sme.entity.Status;
import com.sme.service.AccountTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class AccountTransactionController {

    @Autowired
    private AccountTransactionService accountTransactionService;

    // ✅ Create a new transaction
    @PostMapping("/create")
    public AccountTransaction createTransaction(@RequestBody AccountTransaction transaction) {
        return accountTransactionService.createTransaction(transaction);
    }

    // ✅ Get transactions for a specific account (with status filtering)
    @GetMapping("/account/{accountId}")
    public List<AccountTransaction> getTransactionsByAccountIdAndStatus(
            @PathVariable Long accountId,
            @RequestParam Status status) {
        return accountTransactionService.getTransactionsByAccountIdAndStatus(accountId, status);
    }

    // ✅ Get a single transaction by ID
    @GetMapping("/{id}")
    public Optional<AccountTransaction> getTransactionById(@PathVariable Long id) {
        return accountTransactionService.getTransactionById(id);
    }

    // ✅ Soft delete a transaction
    @DeleteMapping("/delete/{id}")
    public String softDeleteTransaction(@PathVariable Long id) {
        accountTransactionService.softDeleteTransaction(id);
        return "Transaction soft-deleted successfully!";
    }

    // ✅ Get all soft deleted transactions
    @GetMapping("/deleted")
    public List<AccountTransaction> getDeletedTransactions() {
        return accountTransactionService.getDeletedTransactions();
    }
}
