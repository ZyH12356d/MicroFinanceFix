//package com.sme.controller;
//
//import com.sme.dto.HpProductDTO;
//
//import com.sme.service.HpProductService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/products")
//public class HpProductController {
//
//    @Autowired
//    private HpProductService productService;
//
//    // ✅ Create HP Product
//    @PostMapping
//    public ResponseEntity<HpProductDTO> createHpProduct(@RequestBody HpProductDTO productDTO) {
//        return ResponseEntity.ok(productService.createHpProduct(productDTO));
//    }
//
//    // ✅ Get All HP Products
//    @GetMapping
//    public ResponseEntity<List<HpProductDTO>> getAllProducts() {
//        return ResponseEntity.ok(productService.getAllProducts());
//    }
//    // ✅ Create Product
//    @PostMapping
//    public ResponseEntity<HpProductDTO> createProduct(@RequestBody HpProductDTO productDTO) {
//        return ResponseEntity.ok(productService.createHpProduct(productDTO));
//    }
//
//    // ✅ Get All Active Products
//    @GetMapping
//    public ResponseEntity<List<HpProductDTO>> getAllProducts() {
//        return ResponseEntity.ok(productService.getAllActiveProducts());
//    }
//
//    // ✅ Get Product by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<HpProductDTO> getProductById(@PathVariable Long id) {
//        return ResponseEntity.ok(productService.getProductById(id));
//    }
//
//    // ✅ Delete Product (Soft Delete)
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
//        productService.deleteProduct(id);
//        return ResponseEntity.ok("Product marked as inactive.");
//    }
//
//    @GetMapping("/by-product-type/{productTypeId}")
//    public ResponseEntity<List<HpProductDTO>> getProductsByProductType(@PathVariable Long productTypeId) {
//        return ResponseEntity.ok(productService.getProductsByProductType(productTypeId));
//    }
//
//    @GetMapping("/by-dealer/{dealerId}")
//    public ResponseEntity<List<HpProductDTO>> getProductsByDealer(@PathVariable Long dealerId) {
//        return ResponseEntity.ok(productService.getProductsByDealer(dealerId));
//
//    }
//}
