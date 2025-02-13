package com.sme.repository;

import com.sme.entity.ProductType;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
import com.sme.entity.Status;
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {

<<<<<<< Updated upstream
<<<<<<< Updated upstream
    List<ProductType> findByStatus(Status status);
=======
    List<ProductType> findByStatus(Integer status);
>>>>>>> Stashed changes
=======
    List<ProductType> findByStatus(Integer status);
>>>>>>> Stashed changes
}
