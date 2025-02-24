package com.sme.service;

import com.sme.dto.CurrentAccountDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface CurrentAccountService {
    List<CurrentAccountDTO> getAllCurrentAccounts();
    Optional<CurrentAccountDTO> getCurrentAccountById(Long id);
    CurrentAccountDTO createCurrentAccount(CurrentAccountDTO accountDTO);
    void deleteCurrentAccount(Long id);

    boolean hasCurrentAccount(Long id);
}
