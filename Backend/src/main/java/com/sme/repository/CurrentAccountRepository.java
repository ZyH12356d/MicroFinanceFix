package com.sme.repository;

import com.sme.entity.CurrentAccount;
import com.sme.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrentAccountRepository extends JpaRepository<CurrentAccount, Long> {

    // Fetch all accounts with ACTIVE status (for active accounts only)
    @Query("SELECT c FROM CurrentAccount c WHERE c.status = 1")
    List<CurrentAccount> findAllByStatus(Status status);

    // Fetch a specific account by ID and ensure it is ACTIVE (soft delete logic)
    @Query("SELECT c FROM CurrentAccount c WHERE c.id = :id AND c.status = :status")
    Optional<CurrentAccount> findByIdAndStatus(Long id, Status status);

    // Fetch all accounts with INACTIVE status (for soft-deleted accounts)
    @Query("SELECT c FROM CurrentAccount c WHERE c.status = :status")
    List<CurrentAccount> findAllInactiveAccounts(Status status);

    // Find an account by its account number and its status (e.g., ACTIVE)
//    Optional<CurrentAccount> findByAccountNumberAndStatus(String accountNumber, Status status);

    boolean existsByCifId(Long cifId);
}
