package com.sme.repository;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
import com.sme.entity.HpProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HpProductRepository extends JpaRepository<HpProduct, Long> {
=======
=======
>>>>>>> Stashed changes
import com.sme.entity.DealerRegistration;
import com.sme.entity.HpProduct;
import com.sme.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HpProductRepository extends JpaRepository<HpProduct, Long> {

    // ✅ Find active products
    List<HpProduct> findByStatus(Integer status);

    // ✅ Find products by product type
    List<HpProduct> findByProductType(ProductType productType);

    List<HpProduct> findByDealerRegistration(DealerRegistration dealerRegistration);
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
}
