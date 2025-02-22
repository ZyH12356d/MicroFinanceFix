package com.sme.service;

import com.sme.dto.SmeLoanRegistrationDTO;
import com.sme.entity.Collateral;
import com.sme.entity.SmeLoanCollateral;
import com.sme.entity.SmeLoanRegistration;
import com.sme.repository.SmeLoanCollateralRepository;
import com.sme.repository.SmeLoanRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SmeLoanRegistrationService {

    @Autowired
    private SmeLoanRegistrationRepository smeLoanRegistrationRepository;

    @Autowired
    private SmeLoanCollateralRepository smeLoanCollateralRepository;

    private final SmeLoanRegistrationRepository loanRepository;
    private final ModelMapper modelMapper;

    public List<SmeLoanRegistrationDTO> getAllLoans() {
        return loanRepository.findAll().stream()
                .map(loan -> modelMapper.map(loan, SmeLoanRegistrationDTO.class))
                .collect(Collectors.toList());
    }

    public SmeLoanRegistrationDTO getLoanById(Long id) {
        return loanRepository.findById(id)
                .map(loan -> modelMapper.map(loan, SmeLoanRegistrationDTO.class))
                .orElseThrow(() -> new RuntimeException("Loan not found with ID: " + id));
    }

    @Transactional
    public SmeLoanRegistrationDTO createLoan(SmeLoanRegistrationDTO dto) {
        SmeLoanRegistration loan = modelMapper.map(dto, SmeLoanRegistration.class);
        loan = loanRepository.save(loan);
        return modelMapper.map(loan, SmeLoanRegistrationDTO.class);
    }

    public SmeLoanRegistration updateLoan(Long id, SmeLoanRegistration updatedLoan) {
        Optional<SmeLoanRegistration> existingLoanOpt = smeLoanRegistrationRepository.findById(id);

        if (existingLoanOpt.isEmpty()) {
            throw new IllegalArgumentException("SME Loan not found with ID: " + id);
        }

        SmeLoanRegistration existingLoan = existingLoanOpt.get();
        BigDecimal totalCollateralAmount = BigDecimal.ZERO;
        for (SmeLoanCollateral smeLoanCollateral : updatedLoan.getSmeLoanCollaterals()) {
            Collateral collateral = smeLoanCollateral.getCollateral();
            BigDecimal collateralValue = collateral.getValue();
            BigDecimal collateralAmount = smeLoanCollateral.getCollateralAmount();

            // Check if collateral amount exceeds collateral value
            if (collateralAmount.compareTo(collateralValue) > 0) {
                throw new IllegalArgumentException("Collateral amount " + collateralAmount +
                        " exceeds collateral value " + collateralValue);
            }

            totalCollateralAmount = totalCollateralAmount.add(collateralAmount);
        }

        // Validate total collateral amount against loan amount
        if (totalCollateralAmount.compareTo(updatedLoan.getLoanAmount()) < 0) {
            throw new IllegalArgumentException("Total collateral amount must be greater than or equal to loan amount");
        }

        // Update existing loan details
        existingLoan.setLoanAmount(updatedLoan.getLoanAmount());
        existingLoan.setInterestRate(updatedLoan.getInterestRate());
        existingLoan.setGracePeriod(updatedLoan.getGracePeriod());
        existingLoan.setRepaymentDuration(updatedLoan.getRepaymentDuration());
        existingLoan.setDocumentFee(updatedLoan.getDocumentFee());
        existingLoan.setServiceCharges(updatedLoan.getServiceCharges());
        existingLoan.setStatus(updatedLoan.getStatus());
        existingLoan.setDueDate(updatedLoan.getDueDate());
        existingLoan.setRepaymentStartDate(updatedLoan.getRepaymentStartDate());
        existingLoan.setCurrentAccount(updatedLoan.getCurrentAccount());

        // Update collateral information
        smeLoanCollateralRepository.deleteBySmeLoan(existingLoan); // Delete old collaterals
        existingLoan.setSmeLoanCollaterals(updatedLoan.getSmeLoanCollaterals()); // Add new collaterals

        return smeLoanRegistrationRepository.save(existingLoan);
    }

    public SmeLoanRegistration registerLoan(SmeLoanRegistration loan) {
        BigDecimal totalCollateralAmount = BigDecimal.ZERO;

        for (SmeLoanCollateral smeLoanCollateral : loan.getSmeLoanCollaterals()) {
            Collateral collateral = smeLoanCollateral.getCollateral();
            BigDecimal collateralValue = collateral.getValue();
            BigDecimal collateralAmount = smeLoanCollateral.getCollateralAmount();

            // Check if collateral amount exceeds collateral value
            if (collateralAmount.compareTo(collateralValue) > 0) {
                throw new IllegalArgumentException("Collateral amount " + collateralAmount +
                        " exceeds collateral value " + collateralValue);
            }

            totalCollateralAmount = totalCollateralAmount.add(collateralAmount);
        }

        // Validate total collateral amount against loan amount
        if (totalCollateralAmount.compareTo(loan.getLoanAmount()) < 0) {
            throw new IllegalArgumentException("Total collateral amount must be greater than or equal to loan amount");
        }

        return smeLoanRegistrationRepository.save(loan);
    }
}
