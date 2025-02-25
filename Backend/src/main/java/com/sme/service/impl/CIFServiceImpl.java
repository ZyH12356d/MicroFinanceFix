package com.sme.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.sme.dto.CIFDTO;
import com.sme.entity.Branch;
import com.sme.entity.CIF;
import com.sme.repository.BranchRepository;
import com.sme.repository.CIFRepository;
import com.sme.service.CIFService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CIFServiceImpl implements CIFService {

    private final CIFRepository cifRepository;
    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;

    @Autowired
    private CurrentAccountServiceImpl currentAccountService;

    private final Cloudinary cloudinary;

    @Override
    public List<CIFDTO> getAllCIFs() {
        return cifRepository.findAll().stream()
                .map(cif -> modelMapper.map(cif, CIFDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CIFDTO> getCIFById(Long id) {
        return cifRepository.findById(id).map(cif -> {
            boolean hasCurrentAccount = currentAccountService.hasCurrentAccount(cif.getId());
            CIFDTO cifDTO = modelMapper.map(cif, CIFDTO.class);
            cifDTO.setHasCurrentAccount(hasCurrentAccount);
            return cifDTO;
        });
    }

    @Override
    @Transactional
    public CIFDTO createCIF(CIFDTO cifDTO, MultipartFile frontNrc, MultipartFile backNrc) throws IOException {
        CIF cif = modelMapper.map(cifDTO, CIF.class);
        cif.setCreatedAt(LocalDateTime.now());

        // ✅ Upload NRC Images to Cloudinary
        if (frontNrc != null && !frontNrc.isEmpty()) {
            String frontNrcUrl = uploadImage(frontNrc);
            cif.setF_nrcPhoto(frontNrcUrl);
        }

        if (backNrc != null && !backNrc.isEmpty()) {
            String backNrcUrl = uploadImage(backNrc);
            cif.setB_nrcPhoto(backNrcUrl);
        }

        // ✅ Find Branch
        Branch branch = branchRepository.findById(cifDTO.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found with ID: " + cifDTO.getBranchId()));
        cif.setBranch(branch);

        // ✅ Save to Database
        CIF savedCIF = cifRepository.save(cif);
        return modelMapper.map(savedCIF, CIFDTO.class);
    }

    // ✅ Upload Image to Cloudinary
    private String uploadImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("secure_url").toString();
    }



    @Override
    @Transactional
    public CIFDTO updateCIF(Long id, CIFDTO cifDTO) throws IOException {
        CIF cif = cifRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CIF not found with ID: " + id));

        // ✅ Update details
        modelMapper.map(cifDTO, cif);


        CIF updatedCIF = cifRepository.save(cif);
        return modelMapper.map(updatedCIF, CIFDTO.class);
    }

    @Override
    public void deleteCIF(Long id) {
        if (!cifRepository.existsById(id)) {
            throw new RuntimeException("CIF not found with ID: " + id);
        }
        cifRepository.deleteById(id);
    }

}
