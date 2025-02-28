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
        if (transaction == null || transaction.getCurrentAccount() == null) {
            throw new IllegalArgumentException("Transaction or associated account cannot be null.");
        }

        Long accountId = transaction.getCurrentAccount().getId();
        CurrentAccount account = currentAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Current Account not found."));

        BigDecimal amount = transaction.getAmount();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction amount must be greater than zero.");
        }

        TransactionType type = transaction.getTransactionType();
        if (type == null) {
            throw new IllegalArgumentException("Transaction type must be specified (CREDIT/DEBIT).");
        }

        // ✅ Ensure the account is ACTIVE before allowing transactions
        if (!(1 == account.getStatus())) {
            throw new IllegalArgumentException("Transaction cannot be processed. Account is not ACTIVE.");
        }

        BigDecimal newBalance;

        if (type == TransactionType.CREDIT) {
            // ✅ Handle deposits (CREDIT)
            newBalance = account.getBalance().add(amount);
        } else {
            // ✅ Handle withdrawals (DEBIT)
            newBalance = account.getBalance().subtract(amount);

            if (newBalance.compareTo(account.getMinimumBalance()) < 0) {
                throw new IllegalArgumentException("Insufficient balance. Minimum balance must be maintained.");
            }
        }

        // ✅ Update balance and save account
        account.setBalance(newBalance);
        currentAccountRepository.save(account);

        // ✅ Set transaction status as ACTIVE and save transaction
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
