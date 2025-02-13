package com.sme.service;

import com.sme.dto.HpProductDTO;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
import com.sme.entity.HpProduct;
import com.sme.entity.DealerRegistration;
import com.sme.entity.ProductType;
import com.sme.repository.HpProductRepository;
import com.sme.repository.DealerRegistrationRepository;
=======
=======
>>>>>>> Stashed changes
import com.sme.entity.DealerRegistration;
import com.sme.entity.HpProduct;
import com.sme.entity.ProductType;
import com.sme.entity.Status;
import com.sme.repository.DealerRegistrationRepository;
import com.sme.repository.HpProductRepository;
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    private DealerRegistrationRepository dealerRegistrationRepository;
=======
    private ModelMapper modelMapper;
>>>>>>> Stashed changes
=======
    private ModelMapper modelMapper;
>>>>>>> Stashed changes

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    private ModelMapper modelMapper;

    // ✅ Create HP Product (Ensure commissionFee is set)
=======
    private DealerRegistrationRepository dealerRegistrationRepository;

    // ✅ Create Product
>>>>>>> Stashed changes
=======
    private DealerRegistrationRepository dealerRegistrationRepository;

    // ✅ Create Product
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
<<<<<<< Updated upstream
    // ✅ Get All HP Products
    public List<HpProductDTO> getAllProducts() {
        List<HpProduct> products = hpProductRepository.findAll();
=======
=======
>>>>>>> Stashed changes
    // ✅ Get All Active Products
    public List<HpProductDTO> getAllActiveProducts() {
        List<HpProduct> products = hpProductRepository.findByStatus(1);
        return products.stream()
                .map(product -> modelMapper.map(product, HpProductDTO.class))
                .collect(Collectors.toList());
    }

    // ✅ Get Product by ID
    public HpProductDTO getProductById(Long id) {
        HpProduct product = hpProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        return modelMapper.map(product, HpProductDTO.class);
    }

    // ✅ Delete Product (Soft Delete)
    @Transactional
    public void deleteProduct(Long id) {
        HpProduct product = hpProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        product.setStatus(Status.INACTIVE);
        hpProductRepository.save(product);
    }

    public List<HpProductDTO> getProductsByProductType(Long productTypeId) {
        ProductType productType = productTypeRepository.findById(productTypeId)
                .orElseThrow(() -> new RuntimeException("Product Type not found with ID: " + productTypeId));

        List<HpProduct> products = hpProductRepository.findByProductType(productType);
        return products.stream()
                .map(product -> modelMapper.map(product, HpProductDTO.class))
                .collect(Collectors.toList());
    }

    public List<HpProductDTO> getProductsByDealer(Long dealerId) {
        DealerRegistration dealer = dealerRegistrationRepository.findById(dealerId)
                .orElseThrow(() -> new RuntimeException("Dealer not found with ID: " + dealerId));

        List<HpProduct> products = hpProductRepository.findByDealerRegistration(dealer);
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
        return products.stream()
                .map(product -> modelMapper.map(product, HpProductDTO.class))
                .collect(Collectors.toList());
    }
}
