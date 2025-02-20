package com.sme.service;

import com.sme.dto.SmeLoanRegistrationDTO;
import com.sme.entity.SmeLoanRegistration;
import com.sme.repository.SmeLoanRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SmeLoanRegistrationService {

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
}
