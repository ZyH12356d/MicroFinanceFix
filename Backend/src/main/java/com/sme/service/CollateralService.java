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


public interface CollateralService {

    List<CollateralDTO> getAllCollaterals();

    Optional<CollateralDTO> getCollateralById(Long id);

    CollateralDTO createCollateral(CollateralDTO collateralDTO);

    Optional<CollateralDTO> updateCollateral(Long id, CollateralDTO collateralDTO);

    boolean deleteCollateral(Long id);
}
