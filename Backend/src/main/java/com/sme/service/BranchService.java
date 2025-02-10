package com.sme.service;

import com.sme.dto.BranchDTO;
import com.sme.dto.AddressDTO;
import com.sme.entity.Branch;
import com.sme.entity.Address;
import com.sme.repository.BranchRepository;
import com.sme.repository.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            address.setCity(addressDTO.getCity());
            address.setTown(addressDTO.getTown());
            address.setStreet(addressDTO.getStreet());

            branch.setAddress(address);
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

    // Create a new branch
    public BranchDTO createBranch(BranchDTO branchDTO) {
        Branch branch = convertToEntity(branchDTO);

        // If Address ID is provided, fetch it from DB
        if (branchDTO.getAddress() != null && branchDTO.getAddress().getId() > 0) {
            Optional<Address> existingAddress = addressRepository.findById(branchDTO.getAddress().getId());
            existingAddress.ifPresent(branch::setAddress);
        }

        Branch savedBranch = branchRepository.save(branch);
        return convertToDTO(savedBranch);
    }

    // Update an existing branch
    public BranchDTO updateBranch(Long id, BranchDTO branchDTO) {
        Optional<Branch> optionalBranch = branchRepository.findById(id);

        if (optionalBranch.isPresent()) {
            Branch branch = optionalBranch.get();
            branch.setName(branchDTO.getName());
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
                address.setCity(addressDTO.getCity());
                address.setTown(addressDTO.getTown());
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
