package com.sme.service.impl;

import com.sme.dto.SmeLoanRegistrationDTO;
import com.sme.entity.Collateral;
import com.sme.entity.SmeLoanCollateral;
import com.sme.entity.SmeLoanRegistration;
import com.sme.repository.CollateralRepository;
import com.sme.repository.SmeLoanCollateralRepository;
import com.sme.repository.SmeLoanRegistrationRepository;
import com.sme.service.SmeLoanRegistrationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SmeLoanRegistrationServiceImpl implements SmeLoanRegistrationService {

    @Autowired
    private SmeLoanCollateralRepository smeLoanCollateralRepository;

    @Autowired
    private CollateralRepository collateralRepository;

    private final SmeLoanRegistrationRepository loanRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<SmeLoanRegistrationDTO> getAllLoans() {
        return loanRepository.findAll().stream()
                .map(loan -> modelMapper.map(loan, SmeLoanRegistrationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public SmeLoanRegistrationDTO getLoanById(Long id) {
        return loanRepository.findById(id)
                .map(loan -> modelMapper.map(loan, SmeLoanRegistrationDTO.class))
                .orElseThrow(() -> new RuntimeException("Loan not found with ID: " + id));
    }

    @Transactional
    @Override
    public SmeLoanRegistrationDTO createLoan(SmeLoanRegistrationDTO dto) {
        SmeLoanRegistration loan = modelMapper.map(dto, SmeLoanRegistration.class);
        loan = loanRepository.save(loan);
        return modelMapper.map(loan, SmeLoanRegistrationDTO.class);
    }

    @Override
    public SmeLoanRegistration registerLoan(SmeLoanRegistration loan) {
        BigDecimal totalCollateralAmount = BigDecimal.ZERO;

        for (SmeLoanCollateral smeLoanCollateral : loan.getSmeLoanCollaterals()) {
            Collateral inputCollateral = smeLoanCollateral.getCollateral();

            if (inputCollateral == null || inputCollateral.getId() == null) {
                throw new IllegalArgumentException("Collateral ID is missing or null");
            }

            // Fetch the full Collateral entity from DB without reassigning `collateral`
            Collateral fetchedCollateral = collateralRepository.findById(inputCollateral.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Collateral with ID " + inputCollateral.getId() + " not found"));

            // Assign the fetched collateral back
            smeLoanCollateral.setCollateral(fetchedCollateral);

            BigDecimal collateralValue = fetchedCollateral.getValue();
            BigDecimal collateralAmount = smeLoanCollateral.getCollateralAmount();

            if (collateralValue == null) {
                throw new IllegalArgumentException("Collateral value cannot be null for ID: " + fetchedCollateral.getId());
            }

            if (collateralAmount == null) {
                throw new IllegalArgumentException("Collateral amount cannot be null");
            }

            if (collateralAmount.compareTo(collateralValue) > 0) {
                throw new IllegalArgumentException("Collateral amount " + collateralAmount +
                        " exceeds collateral value " + collateralValue);
            }

            totalCollateralAmount = totalCollateralAmount.add(collateralAmount);
        }

        if (loan.getLoanAmount() == null) {
            throw new IllegalArgumentException("Loan amount cannot be null");
        }

        if (totalCollateralAmount.compareTo(loan.getLoanAmount()) < 0) {
            throw new IllegalArgumentException("Total collateral amount must be greater than or equal to loan amount");
        }

        return loanRepository.save(loan);
    }



}
