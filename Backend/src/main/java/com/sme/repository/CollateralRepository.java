package com.sme.repository;

import com.sme.entity.Collateral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CollateralRepository extends JpaRepository<Collateral, Long> {
    Optional<Collateral> findTopByOrderByIdDesc();
}
