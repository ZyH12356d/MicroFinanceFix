package com.sme.repository;

import com.sme.entity.SmeLoanRegistration;
import com.sme.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SmeLoanRegistrationRepository extends JpaRepository<SmeLoanRegistration, Long> {
    List<SmeLoanRegistration> findByStatus(Integer status);
}
