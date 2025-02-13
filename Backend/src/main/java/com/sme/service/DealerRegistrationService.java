package com.sme.service;

import com.sme.dto.DealerRegistrationDTO;
import com.sme.entity.DealerRegistration;
import com.sme.repository.DealerRegistrationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DealerRegistrationService {

    @Autowired
    private DealerRegistrationRepository dealerRepository;

    @Autowired
    private ModelMapper modelMapper;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
    // ✅ Create Dealer Registration
    @Transactional
    public DealerRegistrationDTO createDealer(DealerRegistrationDTO dealerDTO) {
        DealerRegistration dealer = modelMapper.map(dealerDTO, DealerRegistration.class);
        dealer.setRegistrationDate(LocalDateTime.now());
=======
=======
>>>>>>> Stashed changes
     @Transactional
    public DealerRegistrationDTO createDealer(DealerRegistrationDTO dealerDTO) {
        DealerRegistration dealer = modelMapper.map(dealerDTO, DealerRegistration.class);
        dealer.setRegistrationDate(LocalDateTime.now()); // Set registration date
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
        DealerRegistration savedDealer = dealerRepository.save(dealer);
        return modelMapper.map(savedDealer, DealerRegistrationDTO.class);
    }

    // ✅ Get All Dealers
    public List<DealerRegistrationDTO> getAllDealers() {
        List<DealerRegistration> dealers = dealerRepository.findAll();
        return dealers.stream()
                .map(dealer -> modelMapper.map(dealer, DealerRegistrationDTO.class))
                .collect(Collectors.toList());
    }

<<<<<<< Updated upstream
<<<<<<< Updated upstream
    // ✅ Get Dealer by ID
    public DealerRegistrationDTO getDealerById(Long id) {
=======
     public DealerRegistrationDTO getDealerById(Long id) {
>>>>>>> Stashed changes
=======
     public DealerRegistrationDTO getDealerById(Long id) {
>>>>>>> Stashed changes
        DealerRegistration dealer = dealerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dealer not found with ID: " + id));
        return modelMapper.map(dealer, DealerRegistrationDTO.class);
    }

<<<<<<< Updated upstream
<<<<<<< Updated upstream
    // ✅ Update Dealer
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
    @Transactional
    public DealerRegistrationDTO updateDealer(Long id, DealerRegistrationDTO dealerDTO) {
        DealerRegistration existingDealer = dealerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dealer not found with ID: " + id));

        existingDealer.setCompanyName(dealerDTO.getCompanyName());
        existingDealer.setPhoneNumber(dealerDTO.getPhoneNumber());
        existingDealer.setStatus(dealerDTO.getStatus());
        existingDealer.setAddressId(dealerDTO.getAddressId());
        existingDealer.setCurrentAccountId(dealerDTO.getCurrentAccountId());

        DealerRegistration updatedDealer = dealerRepository.save(existingDealer);
        return modelMapper.map(updatedDealer, DealerRegistrationDTO.class);
    }

    // ✅ Delete Dealer
    @Transactional
    public void deleteDealer(Long id) {
        DealerRegistration dealer = dealerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dealer not found with ID: " + id));
        dealerRepository.delete(dealer);
    }
}
