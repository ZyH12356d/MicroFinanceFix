package com.sme.repository;

import com.sme.entity.HpProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HpProductRepository extends JpaRepository<HpProduct, Long> {
}
