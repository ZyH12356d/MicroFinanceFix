package com.sme.controller;

import com.sme.dto.CollateralDTO;
import com.sme.service.CollateralService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/api/collaterals")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS }, allowCredentials = "true")
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

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<CollateralDTO> createCollateral(
            @ModelAttribute CollateralDTO collateralDTO,
            @RequestParam(value = "F_collateralPhoto", required = false) MultipartFile frontPhoto,
            @RequestParam(value = "B_collateralPhoto", required = false) MultipartFile backPhoto) throws IOException {
        return ResponseEntity.ok(collateralService.createCollateral(collateralDTO, frontPhoto, backPhoto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollateralDTO> updateCollateral(@PathVariable Long id,
            @RequestBody CollateralDTO collateralDTO) {
        return collateralService.updateCollateral(id, collateralDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollateral(@PathVariable Long id) {
        return collateralService.deleteCollateral(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
