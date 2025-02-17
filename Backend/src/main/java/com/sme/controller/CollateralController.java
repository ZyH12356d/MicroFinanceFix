package com.sme.controller;

import com.sme.dto.CollateralDTO;
import com.sme.service.CollateralService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/collaterals")
@RequiredArgsConstructor
public class CollateralController {

    private final CollateralService collateralService;

    @GetMapping
    public ResponseEntity<List<CollateralDTO>> getAllCollaterals() {
        return ResponseEntity.ok(collateralService.getAllCollaterals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollateralDTO> getCollateralById(@PathVariable Long id) {
        return collateralService.getCollateralById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CollateralDTO> createCollateral(@RequestBody CollateralDTO collateralDTO) {
        return ResponseEntity.ok(collateralService.createCollateral(collateralDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollateralDTO> updateCollateral(@PathVariable Long id, @RequestBody CollateralDTO collateralDTO) {
        return collateralService.updateCollateral(id, collateralDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollateral(@PathVariable Long id) {
        return collateralService.deleteCollateral(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
