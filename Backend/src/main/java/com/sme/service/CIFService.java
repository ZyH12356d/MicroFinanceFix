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


public interface CIFService {

    List<CIFDTO> getAllCIFs();

    Optional<CIFDTO> getCIFById(Long id);

    CIFDTO createCIF(CIFDTO cifDTO) throws IOException;

    CIFDTO updateCIF(Long id, CIFDTO cifDTO) throws IOException;

    void deleteCIF(Long id);
}