package com.sme.service;

import com.sme.dto.BranchDTO;
import com.sme.dto.AddressDTO;
import com.sme.entity.Branch;
import com.sme.entity.Address;
import com.sme.repository.BranchRepository;
import com.sme.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public interface BranchService {

    String generateBranchCode(String region, String township);

    String getTownshipCode(String township);

    String getRegionCode(String region);

   BranchDTO createBranch(BranchDTO branchDTO, AddressDTO addressDTO);

   BranchDTO convertToDTO(Branch branch);

   List<BranchDTO> getAllBranches();

   Optional<BranchDTO> getBranchById(Long id);

   BranchDTO updateBranch(Long id, BranchDTO branchDTO);

   void deleteBranch(Long id);


}
