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

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private AddressRepository addressRepository; // To fetch Address from DB

    @Autowired
    private ModelMapper modelMapper;

    private String generateBranchCode() {
        String lastBranchCode = branchRepository.findLastBranchCode();

        if (lastBranchCode == null || lastBranchCode.isEmpty()) {
            return "B0001"; // First branch
        }

        System.out.println("Last Branch Code: " + lastBranchCode);

        try {
            int lastNumber = Integer.parseInt(lastBranchCode.substring(1));
            int newNumber = lastNumber + 1;
            return "B" + String.format("%04d", newNumber);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing branch code: " + lastBranchCode);
            return "B0001";
        }
    }


    @Transactional
    public BranchDTO createBranch(BranchDTO branchDTO, AddressDTO addressDTO) {
        Address address = modelMapper.map(addressDTO, Address.class);
        addressRepository.save(address);

        Branch branch = modelMapper.map(branchDTO, Branch.class);
        branch.setAddress(address);

        branch.setCreatedDate(new Date());
        branch.setUpdatedDate(new Date());
        branch.setBranchCode(generateBranchCode());

        Branch savedBranch = branchRepository.save(branch);

        return modelMapper.map(savedBranch, BranchDTO.class);
    }


    // Convert Branch entity to DTO
    private BranchDTO convertToDTO(Branch branch) {
        return modelMapper.map(branch, BranchDTO.class);
    }

    // Convert DTO to Branch entity
    private Branch convertToEntity(BranchDTO branchDTO) {
        Branch branch = modelMapper.map(branchDTO, Branch.class);

        if (branchDTO.getAddress() != null) {
            AddressDTO addressDTO = branchDTO.getAddress();

            Address address = new Address();
            address.setId(addressDTO.getId()); // Ensure the ID is set (if updating existing address)
             address.setDistrict(addressDTO.getDistrict());
             address.setRegion(addressDTO.getRegion());
            address.setStreet(addressDTO.getStreet());

            branch.setAddress(address);
        } else {
            System.out.println("something went wrong");
        }

        return branch;
    }

    // Get all branches (DTO format)
    public List<BranchDTO> getAllBranches() {
        List<Branch> branches = branchRepository.findAll();
        return branches.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Get branch by ID
    public Optional<BranchDTO> getBranchById(Long id) {
        Optional<Branch> branch = branchRepository.findById(id);
        return branch.map(this::convertToDTO);
    }



    // Update an existing branch
    public BranchDTO updateBranch(Long id, BranchDTO branchDTO) {
        Optional<Branch> optionalBranch = branchRepository.findById(id);

        if (optionalBranch.isPresent()) {
            Branch branch = optionalBranch.get();
            branch.setName(branchDTO.getBranchName());
            branch.setBranchCode(branchDTO.getBranchCode());
            branch.setPhoneNumber(branchDTO.getPhoneNumber());
            branch.setEmail(branchDTO.getEmail());
            branch.setStatus(branchDTO.getStatus());
            branch.setCreatedDate(branchDTO.getCreatedDate());
            branch.setUpdatedDate(branchDTO.getUpdatedDate());

            // Convert and update Address if provided
            if (branchDTO.getAddress() != null) {
                AddressDTO addressDTO = branchDTO.getAddress();
                Address address = new Address();
                address.setId(addressDTO.getId());
                address.setDistrict(addressDTO.getDistrict());
                address.setStreet(addressDTO.getStreet());

                branch.setAddress(address);
            }

            Branch updatedBranch = branchRepository.save(branch);
            return convertToDTO(updatedBranch);
        } else {
            throw new RuntimeException("Branch not found with id: " + id);
        }
    }

    // Delete a branch
    public void deleteBranch(Long id) {
        branchRepository.deleteById(id);
    }
}
