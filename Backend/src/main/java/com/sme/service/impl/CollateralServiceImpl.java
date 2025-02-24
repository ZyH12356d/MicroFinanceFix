package com.sme.service.impl;

import com.sme.dto.CollateralDTO;
import com.sme.entity.CIF;
import com.sme.entity.Collateral;
import com.sme.repository.CollateralRepository;
import com.sme.service.CollateralService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CollateralServiceImpl implements CollateralService {

    private final CollateralRepository collateralRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CollateralDTO> getAllCollaterals() {
        return collateralRepository.findAll().stream()
                .map(collateral -> modelMapper.map(collateral, CollateralDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CollateralDTO> getCollateralById(Long id) {
        return collateralRepository.findById(id)
                .map(collateral -> modelMapper.map(collateral, CollateralDTO.class));
    }

    @Transactional
    @Override
    public CollateralDTO createCollateral(CollateralDTO collateralDTO) {
        if (collateralDTO.getId() != null && collateralRepository.existsById(collateralDTO.getId())) {
            throw new RuntimeException("Collateral with ID " + collateralDTO.getId() + " already exists!");
        }

        Collateral collateral = modelMapper.map(collateralDTO, Collateral.class);

        // ✅ Ensure correct CIF reference
        collateral.setCif(new CIF());
        collateral.getCif().setId(collateralDTO.getCifId());

        collateral.setId(null);  // ✅ Ensure it's treated as new
        collateral = collateralRepository.save(collateral);
        return modelMapper.map(collateral, CollateralDTO.class);
    }

    @Transactional
    @Override
    public Optional<CollateralDTO> updateCollateral(Long id, CollateralDTO collateralDTO) {
        return collateralRepository.findById(id).map(existingCollateral -> {
            modelMapper.map(collateralDTO, existingCollateral);
            existingCollateral.setId(id);  // ✅ Ensure ID remains the same
            existingCollateral = collateralRepository.save(existingCollateral);
            return modelMapper.map(existingCollateral, CollateralDTO.class);
        });
    }


    @Transactional
    @Override
    public boolean deleteCollateral(Long id) {
        if (collateralRepository.existsById(id)) {
            collateralRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
