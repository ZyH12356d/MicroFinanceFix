package com.sme.service;

import com.sme.dto.ProductTypeDTO;
import com.sme.entity.ProductType;
import com.sme.entity.Status;
import com.sme.repository.ProductTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductTypeService {

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    // ✅ Create Product Type
    @Transactional
    public ProductTypeDTO createProductType(ProductTypeDTO productTypeDTO) {
        ProductType productType = modelMapper.map(productTypeDTO, ProductType.class);
 
        productType.setStatus(4); // Default to active
 
        ProductType savedProductType = productTypeRepository.save(productType);
        return modelMapper.map(savedProductType, ProductTypeDTO.class);
    }

    // ✅ Get All Active Product Types
    public List<ProductTypeDTO> getAllActiveProductTypes() {
        List<ProductType> productTypes = productTypeRepository.findByStatus(Status.ACTIVE);
 
        return productTypes.stream()
                .map(productType -> modelMapper.map(productType, ProductTypeDTO.class))
                .collect(Collectors.toList());
    }

    // ✅ Get Product Type by ID
    public ProductTypeDTO getProductTypeById(Long id) {
        ProductType productType = productTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Type not found with ID: " + id));
        return modelMapper.map(productType, ProductTypeDTO.class);
    }

    // ✅ Update Product Type
    @Transactional
    public ProductTypeDTO updateProductType(Long id, ProductTypeDTO productTypeDTO) {
        ProductType existingProductType = productTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Type not found with ID: " + id));

        existingProductType.setName(productTypeDTO.getName());
        existingProductType.setStatus(productTypeDTO.getStatus());

        ProductType updatedProductType = productTypeRepository.save(existingProductType);
        return modelMapper.map(updatedProductType, ProductTypeDTO.class);
    }

    // ✅ Delete Product Type (Soft Delete)
    @Transactional
    public void deleteProductType(Long id) {
        ProductType productType = productTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Type not found with ID: " + id));
 
        productType.setStatus(2);
        productTypeRepository.save(productType);
    }
}
