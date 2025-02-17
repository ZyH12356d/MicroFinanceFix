package com.sme.service;

import com.sme.dto.HpProductDTO;
import com.sme.entity.HpProduct;
import com.sme.entity.DealerRegistration;
import com.sme.entity.ProductType;
import com.sme.repository.HpProductRepository;
import com.sme.repository.DealerRegistrationRepository;
import com.sme.repository.ProductTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HpProductService {

    @Autowired
    private HpProductRepository hpProductRepository;

    @Autowired
    private DealerRegistrationRepository dealerRegistrationRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    // ✅ Create HP Product (Ensure commissionFee is set)
    @Transactional
    public HpProductDTO createHpProduct(HpProductDTO productDTO) {
        HpProduct product = modelMapper.map(productDTO, HpProduct.class);

        // ✅ Ensure ProductType exists
        ProductType productType = productTypeRepository.findById(productDTO.getProductTypeId())
                .orElseThrow(() -> new RuntimeException("Product Type not found with ID: " + productDTO.getProductTypeId()));

        // ✅ Ensure Dealer exists
        DealerRegistration dealer = dealerRegistrationRepository.findById(productDTO.getDealerRegistrationId())
                .orElseThrow(() -> new RuntimeException("Dealer not found with ID: " + productDTO.getDealerRegistrationId()));

        product.setProductType(productType);
        product.setDealerRegistration(dealer);

        // ✅ Ensure `commissionFee` is not null (default to 0 if missing)
        if (product.getCommissionFee() == null) {
            product.setCommissionFee(BigDecimal.ZERO);
        }

        HpProduct savedProduct = hpProductRepository.save(product);
        return modelMapper.map(savedProduct, HpProductDTO.class);
    }

    // ✅ Get All HP Products
    public List<HpProductDTO> getAllProducts() {
        List<HpProduct> products = hpProductRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, HpProductDTO.class))
                .collect(Collectors.toList());
    }
}
