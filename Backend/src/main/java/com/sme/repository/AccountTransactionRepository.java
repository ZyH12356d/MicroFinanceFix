package com.sme.repository;

import com.sme.entity.AccountTransaction;
import com.sme.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {

    // ✅ Find transactions by CurrentAccount ID and status (ACTIVE, INACTIVE, etc.)
    List<AccountTransaction> findByCurrentAccountIdAndStatus(Long accountId, Status status);

    // ✅ Find all soft-deleted transactions (where status is INACTIVE)
    List<AccountTransaction> findByStatus(Status status);
}
