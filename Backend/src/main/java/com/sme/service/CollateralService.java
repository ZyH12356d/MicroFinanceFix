package com.sme.service;

 import com.sme.dto.CollateralDTO;
 import com.sme.entity.CIF;
 import com.sme.entity.Collateral;
 import com.sme.repository.CIFRepository;
 import com.sme.repository.CollateralRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CollateralService {
@Autowired
    private CollateralRepository collateralRepository;

    @Autowired
    private CIFRepository cifRepository;

    @Autowired
    private ModelMapper modelMapper;



    public List<CollateralDTO> getAllCollaterals() {
        List<Collateral> collaterals = collateralRepository.findAll();
        return collaterals.stream()
                .map(collateral -> modelMapper.map(collateral, CollateralDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<CollateralDTO> getCollateralById(Long id) {
        Optional<Collateral> collateral = collateralRepository.findById(id);
        return collateral.map(c -> modelMapper.map(c, CollateralDTO.class));
    }

//    public CollateralDTO saveCollateral(CollateralDTO collateralDTO) {
//        Collateral collateral = modelMapper.map(collateralDTO, Collateral.class);
//        Collateral savedCollateral = collateralRepository.save(collateral);
//        return modelMapper.map(savedCollateral, CollateralDTO.class);
//    }

    public CollateralDTO updateCollateral(Long id, Collateral updatedCollateral) {
        // Find existing collateral
        Collateral existingCollateral = collateralRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Collateral not found"));

        // Update fields
        existingCollateral.setCollateralType(updatedCollateral.getCollateralType());
        existingCollateral.setValue(updatedCollateral.getValue());
        existingCollateral.setDescription(updatedCollateral.getDescription());
        existingCollateral.setStatus(updatedCollateral.getStatus());
        existingCollateral.setDate(updatedCollateral.getDate());
        existingCollateral.setCollateralCode(updatedCollateral.getCollateralCode());
        existingCollateral.setCif(updatedCollateral.getCif());

        // Save and return updated DTO
        Collateral savedCollateral = collateralRepository.save(existingCollateral);
        return modelMapper.map(savedCollateral, CollateralDTO.class);
    }


    public void deleteCollateral(Long id) {
        collateralRepository.deleteById(id);
    }

    public CollateralDTO saveCollateral(Collateral collateral) {
        Collateral savedCollateral = collateralRepository.save(collateral);
        return modelMapper.map(savedCollateral, CollateralDTO.class);
    }

}

