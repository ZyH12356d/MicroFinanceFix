package com.sme.controller;

import com.sme.dto.CIFDTO;
import com.sme.service.CIFService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cifs")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class CIFController {

    private final CIFService cifService;

    // ✅ Get All CIFs
    @GetMapping
    public ResponseEntity<List<CIFDTO>> getAllCIFs() {
        List<CIFDTO> cifList = cifService.getAllCIFs();
        return ResponseEntity.ok(cifList);
    }

    // ✅ Get CIF by ID
    @GetMapping("/{id}")
    public ResponseEntity<CIFDTO> getCIFById(@PathVariable Long id) {
        return cifService.getCIFById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CIFDTO> createCIF(
            @RequestBody CIFDTO cifdto
    ) throws IOException {
         return ResponseEntity.ok(cifService.createCIF(cifdto));
    }


    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<CIFDTO> updateCIF(
            @PathVariable Long id,
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

        CIFDTO cifDTO = CIFDTO.builder()
                .name(name)
                .nrcNumber(nrcNumber)
                .dob(LocalDate.parse(dob))
                .gender(gender)
                .phoneNumber(phoneNumber)
                .email(email)
                .address(address)
                .maritalStatus(maritalStatus)
                .occupation(occupation)
                .incomeSource(incomeSource)
                .branchId(branchId)
                .build();

        // 🔥 Pass all required arguments
        return ResponseEntity.ok(cifService.updateCIF(id, cifDTO));
    }


    // ✅ Delete CIF
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCIF(@PathVariable Long id) {
        cifService.deleteCIF(id);
        return ResponseEntity.noContent().build();
    }
}
