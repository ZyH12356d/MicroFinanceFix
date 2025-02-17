package com.sme.repository;

import com.sme.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    @Query("SELECT b.branchCode FROM Branch b ORDER BY b.id DESC LIMIT 1")
    String findLastBranchCode();


}
