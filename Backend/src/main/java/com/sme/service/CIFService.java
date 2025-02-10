package com.sme.service;


import com.sme.dto.CIFDTO;
import com.sme.entity.Branch;
import com.sme.entity.CIF;
import com.sme.repository.BranchRepository;
import com.sme.repository.CIFRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CIFService {

    @Autowired
    private CIFRepository cifRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private CloudinaryService cloudinaryService; // Cloudinary Service for Image Uploads

    @Autowired
    private ModelMapper modelMapper;

    // Convert Entity → DTO
    private CIFDTO convertToDTO(CIF cif) {
        CIFDTO dto = modelMapper.map(cif, CIFDTO.class);
        dto.setBranchId(cif.getBranch().getId());
        return dto;
    }

    // Convert DTO → Entity
    private CIF convertToEntity(CIFDTO dto) {
        CIF cif = modelMapper.map(dto, CIF.class);

        Optional<Branch> branch = branchRepository.findById(dto.getBranchId());
        branch.ifPresent(cif::setBranch);

        return cif;
    }

    // Get all CIF records
    public List<CIFDTO> getAllCIFs() {
        List<CIF> cifs = cifRepository.findAll();
        return cifs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Get CIF by ID
    public Optional<CIFDTO> getCIFById(Long id) {
        Optional<CIF> cif = cifRepository.findById(id);
        return cif.map(this::convertToDTO);
    }

    // Create a new CIF with NRC Uploads
    public CIFDTO createCIF(CIFDTO cifDTO) throws IOException {
        CIF cif = convertToEntity(cifDTO);
        cif.setCreatedAt(LocalDateTime.now());

        // Upload NRC images to Cloudinary
        if (cifDTO.getFrontNrc() != null && !cifDTO.getFrontNrc().isEmpty()) {
            String frontNrcUrl = cloudinaryService.uploadImage(cifDTO.getFrontNrc());
            cif.setF_nrcPhoto(frontNrcUrl);
        }

        if (cifDTO.getBackNrc() != null && !cifDTO.getBackNrc().isEmpty()) {
            String backNrcUrl = cloudinaryService.uploadImage(cifDTO.getBackNrc());
            cif.setB_nrcPhoto(backNrcUrl);
        }

        CIF savedCIF = cifRepository.save(cif);
        return convertToDTO(savedCIF);
    }

    // Delete CIF
    public void deleteCIF(Long id) {
        cifRepository.deleteById(id);
    }
}
