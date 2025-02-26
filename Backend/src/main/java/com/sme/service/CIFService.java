package com.sme.service;


import com.sme.dto.CIFDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface CIFService {

    List<CIFDTO> getAllCIFs();

    Optional<CIFDTO> getCIFById(Long id);

    CIFDTO createCIF(CIFDTO cifDTO, MultipartFile frontNrc, MultipartFile backNrc) throws IOException;

    CIFDTO updateCIF(Long id, CIFDTO cifDTO) throws IOException;

    void deleteCIF(Long id);

    Page<CIFDTO> getAllCIFsPaged(Pageable pageable);

}