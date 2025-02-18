package com.sme.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sme.dto.AddressDTO;
import com.sme.dto.BranchDTO;
import com.sme.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/branches")
@CrossOrigin(origins = "http://localhost:4200")
public class BranchController {

    @Autowired
    private BranchService branchService;

    // Get all branches
    @GetMapping
    public List<BranchDTO> getAllBranches() {
        return branchService.getAllBranches();
    }

    // Get branch by ID
    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> getBranchById(@PathVariable Long id) {
        Optional<BranchDTO> branch = branchService.getBranchById(id);
        return branch.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new branch
    @PostMapping
    public ResponseEntity<BranchDTO> createBranch(@RequestBody Map<String, Object> request) {
        ObjectMapper objectMapper = new ObjectMapper();

        BranchDTO branchDTO = objectMapper.convertValue(request.get("branch"), BranchDTO.class);
        AddressDTO addressDTO = objectMapper.convertValue(request.get("address"), AddressDTO.class);

        BranchDTO savedBranch = branchService.createBranch(branchDTO, addressDTO);

        return ResponseEntity.ok(savedBranch);
    }

    // Update an existing branch
    @PutMapping("/{id}")
    public ResponseEntity<BranchDTO> updateBranch(@PathVariable Long id, @RequestBody BranchDTO branchDTO) {
        return ResponseEntity.ok(branchService.updateBranch(id, branchDTO));
    }

    // Delete a branch
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }
}
