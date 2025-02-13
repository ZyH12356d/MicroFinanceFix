package com.sme.service;

import com.sme.dto.SmeLoanRegistrationDTO;
import com.sme.entity.Collateral;
import com.sme.entity.CurrentAccount;
 import com.sme.entity.SmeLoanRegistration;
import com.sme.repository.CollateralRepository;
import com.sme.repository.CurrentAccountRepository;
import com.sme.repository.SmeLoanRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SmeLoanRegistrationService {

    @Autowired
    private SmeLoanRegistrationRepository repository;

    @Autowired
    private CurrentAccountRepository currentAccountRepository;

    @Autowired
    private CollateralRepository collateralRepository;

    public SmeLoanRegistration registerLoan(SmeLoanRegistrationDTO dto) {
        SmeLoanRegistration loan = new SmeLoanRegistration();

        // Fetch Entities
        CurrentAccount currentAccount = currentAccountRepository.findById(dto.getCurrentAccountId())
                .orElseThrow(() -> new RuntimeException("Current Account not found"));

        Collateral collateral = collateralRepository.findById(dto.getCollateralId())
                .orElseThrow(() -> new RuntimeException("Collateral not found"));

        // Set Fields
        loan.setLoanAmount(dto.getLoanAmount());
        loan.setInterestRate(dto.getInterestRate());
        loan.setGracePeriod(dto.getGracePeriod());
        loan.setRepaymentDuration(dto.getRepaymentDuration());
        loan.setDocumentFee(dto.getDocumentFee());
        loan.setServiceCharges(dto.getServiceCharges());
        loan.setStatus(dto.getStatus());
        loan.setDueDate(dto.getDueDate());
        loan.setRepaymentStartDate(dto.getRepaymentStartDate());

        loan.setCurrentAccount(currentAccount);
        loan.setCollateral(collateral);

        return repository.save(loan);
    }

    public List<SmeLoanRegistration> getAllLoans() {
        return repository.findAll();
    }

    public Optional<SmeLoanRegistration> getLoanById(Long id) {
        return repository.findById(id);
    }

    public void deleteLoan(Long id) {
        repository.deleteById(id);
    }
}