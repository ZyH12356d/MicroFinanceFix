package com.sme.service;

import com.sme.dto.CollateralDTO;
import com.sme.entity.CIF;
import com.sme.entity.Collateral;
import com.sme.repository.CollateralRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollateralService {

    private final CollateralRepository collateralRepository;
    private final ModelMapper modelMapper;

    public List<CollateralDTO> getAllCollaterals() {
        return collateralRepository.findAll().stream()
                .map(collateral -> modelMapper.map(collateral, CollateralDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<CollateralDTO> getCollateralById(Long id) {
        return collateralRepository.findById(id)
                .map(collateral -> modelMapper.map(collateral, CollateralDTO.class));
    }

    @Transactional
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
    public Optional<CollateralDTO> updateCollateral(Long id, CollateralDTO collateralDTO) {
        return collateralRepository.findById(id).map(existingCollateral -> {
            modelMapper.map(collateralDTO, existingCollateral);
            existingCollateral.setId(id);  // ✅ Ensure ID remains the same
            existingCollateral = collateralRepository.save(existingCollateral);
            return modelMapper.map(existingCollateral, CollateralDTO.class);
        });
    }


    @Transactional
    public boolean deleteCollateral(Long id) {
        if (collateralRepository.existsById(id)) {
            collateralRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
