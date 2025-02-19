package com.sme.controller;


import com.sme.dto.CIFDTO;
import com.sme.service.CIFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cifs")
@CrossOrigin("http://localhost:4200")
public class CIFController {

    @Autowired
    private CIFService cifService;

    // Get all CIF records
    @GetMapping
    public List<CIFDTO> getAllCIFs() {
        return cifService.getAllCIFs();
    }

    // Get CIF by ID
    @GetMapping("/{id}")
    public ResponseEntity<CIFDTO> getCIFById(@PathVariable Long id) {
        Optional<CIFDTO> cif = cifService.getCIFById(id);
        return cif.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new CIF with NRC Photos
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<CIFDTO> createCIF(
            @RequestParam("name") String name,
            @RequestParam("nrcNumber") String nrcNumber,
            @RequestParam("dob") String dob,
            @RequestParam("gender") String gender,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("email") String email,
            @RequestParam("address") String address,
            @RequestParam("maritalStatus") String maritalStatus,
            @RequestParam("occupation") String occupation,
            @RequestParam("incomeSource") String incomeSource,
            @RequestParam("branchId") Long branchId,
            @RequestParam(value = "frontNrc", required = false) MultipartFile frontNrc,
            @RequestParam(value = "backNrc", required = false) MultipartFile backNrc
    ) throws IOException {

        CIFDTO cifDTO = new CIFDTO();
        cifDTO.setName(name);
        cifDTO.setNrcNumber(nrcNumber);
        cifDTO.setDob(LocalDate.parse(dob));
        cifDTO.setGender(gender);
        cifDTO.setPhoneNumber(phoneNumber);
        cifDTO.setEmail(email);
        cifDTO.setAddress(address);
        cifDTO.setMaritalStatus(maritalStatus);
        cifDTO.setOccupation(occupation);
        cifDTO.setIncomeSource(incomeSource);
        cifDTO.setBranchId(branchId);
        cifDTO.setFrontNrc(frontNrc);
        cifDTO.setBackNrc(backNrc);

        return ResponseEntity.ok(cifService.createCIF(cifDTO));
    }

    // Delete CIF
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCIF(@PathVariable Long id) {
        cifService.deleteCIF(id);
        return ResponseEntity.noContent().build();
    }
}
