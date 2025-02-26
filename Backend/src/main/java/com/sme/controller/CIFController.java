package com.sme.controller;

import com.sme.dto.CIFDTO;
import com.sme.exception.CIFValidationException;
import com.sme.service.CIFService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequiredArgsConstructor
public class CIFController {

    private final CIFService cifService;

    @GetMapping
    public ResponseEntity<List<CIFDTO>> getAllCIFs() {
        List<CIFDTO> cifList = cifService.getAllCIFs();
        return ResponseEntity.ok(cifList);
    }

    @GetMapping("/{id}")
    public Optional<CIFDTO> getCIFById(@PathVariable Long id) {
        return cifService.getCIFById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createCIF(
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
        try {
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

            return ResponseEntity.ok(cifService.createCIF(cifDTO, frontNrc, backNrc));
        } catch (CIFValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<CIFDTO>> getCIFsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CIFDTO> cifPage = cifService.getAllCIFsPaged(pageable);

        return ResponseEntity.ok(cifPage);
    }
}
