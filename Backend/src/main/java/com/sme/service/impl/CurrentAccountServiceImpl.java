package com.sme.service.impl;

import com.sme.dto.CurrentAccountDTO;
import com.sme.entity.CurrentAccount;
import com.sme.entity.CIF;
import com.sme.repository.CurrentAccountRepository;
import com.sme.repository.CIFRepository;
import com.sme.service.CurrentAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CurrentAccountServiceImpl implements CurrentAccountService {

    @Autowired
    private CurrentAccountRepository currentAccountRepository;

    @Autowired
    private CIFRepository cifRepository;

    @Autowired
    private ModelMapper modelMapper;

    // ✅ Convert Entity → DTO
    private CurrentAccountDTO convertToDTO(CurrentAccount account) {
        CurrentAccountDTO dto = modelMapper.map(account, CurrentAccountDTO.class);
        dto.setCifId(account.getCif().getId());
        return dto;
    }

    // ✅ Convert DTO → Entity
    private CurrentAccount convertToEntity(CurrentAccountDTO dto) {
        CurrentAccount account = modelMapper.map(dto, CurrentAccount.class);
        Optional<CIF> cif = cifRepository.findById(dto.getCifId());
        cif.ifPresent(account::setCif);
        return account;
    }

    // ✅ Get all Current Accounts
    @Override
    public List<CurrentAccountDTO> getAllCurrentAccounts() {
        List<CurrentAccount> accounts = currentAccountRepository.findAll();
        return accounts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // ✅ Get Current Account by ID
    @Override
    public Optional<CurrentAccountDTO> getCurrentAccountById(Long id) {
        Optional<CurrentAccount> account = currentAccountRepository.findById(id);
        return account.map(this::convertToDTO);
    }

    // ✅ Create a new Current Account with Min & Max Validation
    @Override
    public CurrentAccountDTO createCurrentAccount(CurrentAccountDTO accountDTO) {
        if (accountDTO.getBalance().compareTo(accountDTO.getMinimumBalance()) < 0) {
            throw new IllegalArgumentException("Balance cannot be less than the minimum balance.");
        }

        if (accountDTO.getBalance().compareTo(accountDTO.getMaximumBalance()) > 0) {
            throw new IllegalArgumentException("Balance cannot be greater than the maximum balance.");
        }

        CurrentAccount account = new CurrentAccount();
        account.setBalance(accountDTO.getBalance());
        account.setMinimumBalance(accountDTO.getMinimumBalance());
        account.setMaximumBalance(accountDTO.getMaximumBalance());
        account.setStatus(accountDTO.getStatus());
        account.setDateCreated(new Date());
        account.setHoldAmount(BigDecimal.ZERO); // Default hold amount

        // Fetch and set CIF reference
        CIF cif = cifRepository.findById(accountDTO.getCifId())
                .orElseThrow(() -> new RuntimeException("CIF not found with ID: " + accountDTO.getCifId()));
        account.setCif(cif);

        CurrentAccount savedAccount = currentAccountRepository.save(account);
        return convertToDTO(savedAccount);
    }

    // ✅ Delete a Current Account
    @Override
    public void deleteCurrentAccount(Long id) {
        currentAccountRepository.deleteById(id);
    }

}
