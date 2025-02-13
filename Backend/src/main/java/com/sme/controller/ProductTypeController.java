package com.sme.controller;

import com.sme.dto.ProductTypeDTO;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
import com.sme.entity.Status;
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
import com.sme.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-types")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    // ✅ Create Product Type
    @PostMapping
    public ResponseEntity<ProductTypeDTO> createProductType(@RequestBody ProductTypeDTO productTypeDTO) {
        return ResponseEntity.ok(productTypeService.createProductType(productTypeDTO));
    }

    // ✅ Get All Active Product Types
    @GetMapping
    public ResponseEntity<List<ProductTypeDTO>> getAllProductTypes() {
        return ResponseEntity.ok(productTypeService.getAllActiveProductTypes());
    }

    // ✅ Get Product Type by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductTypeDTO> getProductTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(productTypeService.getProductTypeById(id));
    }

    // ✅ Update Product Type
    @PutMapping("/{id}")
    public ResponseEntity<ProductTypeDTO> updateProductType(
            @PathVariable Long id,
            @RequestBody ProductTypeDTO productTypeDTO) {
        return ResponseEntity.ok(productTypeService.updateProductType(id, productTypeDTO));
    }

    // ✅ Delete Product Type (Soft Delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductType(@PathVariable Long id) {
<<<<<<< Updated upstream
<<<<<<< Updated upstream
        productTypeService.deleteProductType(2L);
=======
        productTypeService.deleteProductType(id);
>>>>>>> Stashed changes
=======
        productTypeService.deleteProductType(id);
>>>>>>> Stashed changes
        return ResponseEntity.ok("Product Type marked as inactive.");
    }
}
