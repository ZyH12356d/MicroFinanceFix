package com.sme.service;

import com.sme.entity.AccountTransaction;
import com.sme.entity.Status;

import java.util.List;
import java.util.Optional;

public interface AccountTransactionService {

    // Create a new transaction (with min/max validation)
    AccountTransaction createTransaction(AccountTransaction transaction);

    // Retrieve all transactions for an account based on status
    List<AccountTransaction> getTransactionsByAccountIdAndStatus(Long accountId, Status status);

    // Retrieve a single transaction by ID
    Optional<AccountTransaction> getTransactionById(Long id);

    // Soft delete a transaction
    void softDeleteTransaction(Long id);

    // Retrieve all soft-deleted transactions
    List<AccountTransaction> getDeletedTransactions();
}
