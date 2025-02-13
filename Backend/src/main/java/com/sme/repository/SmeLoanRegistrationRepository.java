package com.sme.repository;

import com.sme.entity.SmeLoanRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmeLoanRegistrationRepository extends JpaRepository<SmeLoanRegistration, Long> {
}
