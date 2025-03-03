package com.sme.service.impl;

import com.sme.entity.AccountTransaction;
import com.sme.entity.CurrentAccount;
import com.sme.entity.Status;
import com.sme.entity.TransactionType;
import com.sme.repository.AccountTransactionRepository;
import com.sme.repository.CurrentAccountRepository;
import com.sme.service.AccountTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountTransactionServiceImpl implements AccountTransactionService {

    @Autowired
    private AccountTransactionRepository accountTransactionRepository;

    @Autowired
    private CurrentAccountRepository currentAccountRepository;

    @Override
    public AccountTransaction createTransaction(AccountTransaction transaction) {
        if (transaction == null || transaction.getCurrentAccount() == null || transaction.getCurrentAccount().getId() == null) {
            throw new IllegalArgumentException("Transaction or associated account ID cannot be null.");
        }

        Long accountId = transaction.getCurrentAccount().getId();
        Optional<CurrentAccount> accountOpt = currentAccountRepository.findById(accountId);

        if (accountOpt.isEmpty()) {
            throw new IllegalArgumentException("Current Account not found with ID: " + accountId);
        }

        CurrentAccount account = accountOpt.get();

        if (account.getStatus() != 1) {
            throw new IllegalArgumentException("Transaction cannot be processed. Account is not ACTIVE.");
        }

        BigDecimal amount = transaction.getAmount();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction amount must be greater than zero.");
        }

        TransactionType type = transaction.getTransactionType();
        if (type == null) {
            throw new IllegalArgumentException("Transaction type must be specified (CREDIT/DEBIT).");
        }

        BigDecimal newBalance;
        if (type == TransactionType.CREDIT) {
            newBalance = account.getBalance().add(amount);
        } else {
            newBalance = account.getBalance().subtract(amount);
            if (newBalance.compareTo(account.getMinimumBalance()) < 0) {
                throw new IllegalArgumentException("Insufficient balance. Minimum balance must be maintained.");
            }
        }

        account.setBalance(newBalance);
        currentAccountRepository.save(account);

        transaction.setStatus(Status.ACTIVE.getCode());
        return accountTransactionRepository.save(transaction);
    }




    // ✅ Retrieve transactions by account and status
    @Override
    public List<AccountTransaction> getTransactionsByAccountIdAndStatus(Long accountId, Status status) {
        return accountTransactionRepository.findByCurrentAccountIdAndStatus(accountId, status);
    }

    // ✅ Retrieve a transaction by ID
    @Override
    public Optional<AccountTransaction> getTransactionById(Long id) {
        return accountTransactionRepository.findById(id);
    }

    // ✅ Soft delete a transaction
    @Override
    public void softDeleteTransaction(Long id) {
        Optional<AccountTransaction> transaction = accountTransactionRepository.findById(id);
        if (transaction.isPresent()) {
            AccountTransaction txn = transaction.get();
            txn.setStatus(Status.INACTIVE.getCode());  // Soft delete
            accountTransactionRepository.save(txn);
        } else {
            throw new RuntimeException("Transaction not found.");
        }
    }

    // ✅ Retrieve all soft-deleted transactions
    @Override
    public List<AccountTransaction> getDeletedTransactions() {
        return accountTransactionRepository.findByStatus(Status.INACTIVE);
    }
}
