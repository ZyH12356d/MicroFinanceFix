package com.sme.controller;

import com.sme.dto.CollateralDTO;
import com.sme.entity.CIF;
import com.sme.entity.Collateral;
import com.sme.repository.CIFRepository;
import com.sme.service.CollateralService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/collaterals")
public class CollateralController {

    @Autowired
    private CollateralService collateralService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CIFRepository cifRepository;

    @GetMapping
    public List<CollateralDTO> getAllCollaterals() {
        return collateralService.getAllCollaterals();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollateralDTO> getCollateralById(@PathVariable Long id) {
        Optional<CollateralDTO> collateral = collateralService.getCollateralById(id);
        return collateral.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CollateralDTO> createCollateral(@RequestBody CollateralDTO collateralDTO) {
        if (collateralDTO.getCifId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Ensure CIF exists
        CIF cif = cifRepository.findById(collateralDTO.getCifId())
                .orElseThrow(() -> new RuntimeException("CIF not found"));

        // Convert DTO → Entity
        Collateral collateral = modelMapper.map(collateralDTO, Collateral.class);
        collateral.setCif(cif);  // Set CIF relationship

        // Call service and receive DTO response
        CollateralDTO responseDTO = collateralService.saveCollateral(collateral);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollateralDTO> updateCollateral(@PathVariable Long id, @RequestBody CollateralDTO collateralDTO) {
        if (collateralDTO.getCifId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Ensure CIF exists before updating
        CIF cif = cifRepository.findById(collateralDTO.getCifId())
                .orElseThrow(() -> new RuntimeException("CIF not found"));

        // Convert DTO → Entity
        Collateral collateral = modelMapper.map(collateralDTO, Collateral.class);
        collateral.setCif(cif);  // Set CIF relationship

        // Call service to update collateral and return DTO response
        CollateralDTO updatedDTO = collateralService.updateCollateral(id, collateral);
        return ResponseEntity.ok(updatedDTO);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollateral(@PathVariable Long id) {
        collateralService.deleteCollateral(id);
        return ResponseEntity.noContent().build();
    }
}