package com.sme.service;

import com.sme.dto.CurrentAccountDTO;
import com.sme.entity.CurrentAccount;
import com.sme.entity.CIF;
import com.sme.repository.CurrentAccountRepository;
import com.sme.repository.CIFRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CurrentAccountService {

    @Autowired
    private CurrentAccountRepository currentAccountRepository;

    @Autowired
    private CIFRepository cifRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Convert Entity → DTO
    private CurrentAccountDTO convertToDTO(CurrentAccount account) {
        CurrentAccountDTO dto = modelMapper.map(account, CurrentAccountDTO.class);
        dto.setCifId(account.getCif().getId());
        return dto;
    }

    // Convert DTO → Entity
    private CurrentAccount convertToEntity(CurrentAccountDTO dto) {
        CurrentAccount account = modelMapper.map(dto, CurrentAccount.class);

        // Fetch CIF from DB
        Optional<CIF> cif = cifRepository.findById(dto.getCifId());
        cif.ifPresent(account::setCif);

        return account;
    }

    // Get all Current Accounts
    public List<CurrentAccountDTO> getAllCurrentAccounts() {
        List<CurrentAccount> accounts = currentAccountRepository.findAll();
        return accounts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Get Current Account by ID
    public Optional<CurrentAccountDTO> getCurrentAccountById(Long id) {
        Optional<CurrentAccount> account = currentAccountRepository.findById(id);
        return account.map(this::convertToDTO);
    }

    // ✅ Create a new Current Account with Auto-Generated Serial Number
    public CurrentAccountDTO createCurrentAccount(CurrentAccountDTO accountDTO) {
        CurrentAccount account = new CurrentAccount();
        account.setBalance(accountDTO.getBalance());
        account.setStatus(accountDTO.getStatus());
        account.setDateCreated(new Date());
        account.setHoldAmount(BigDecimal.ZERO); // Default hold amount

        // Fetch and set CIF reference
        CIF cif = cifRepository.findById(accountDTO.getCifId())
                .orElseThrow(() -> new RuntimeException("CIF not found with ID: " + accountDTO.getCifId()));
        account.setCif(cif);

        // Save the entity (Hibernate inserts without accountNumber)
        CurrentAccount savedAccount = currentAccountRepository.save(account);

        // Fetch the updated entity (which now includes the generated accountNumber)
        savedAccount = currentAccountRepository.findById(savedAccount.getId()).orElseThrow();

        return convertToDTO(savedAccount);
    }

    // Delete a Current Account
    public void deleteCurrentAccount(Long id) {
        currentAccountRepository.deleteById(id);
    }
}
