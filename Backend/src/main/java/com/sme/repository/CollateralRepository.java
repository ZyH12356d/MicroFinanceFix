package com.sme.repository;

import com.sme.entity.Collateral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CollateralRepository extends JpaRepository<Collateral, Long> {
}
