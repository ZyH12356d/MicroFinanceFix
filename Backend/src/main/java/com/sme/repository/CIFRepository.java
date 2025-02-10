package com.sme.repository;

import com.sme.entity.CIF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CIFRepository extends JpaRepository<CIF, Long> {
}
