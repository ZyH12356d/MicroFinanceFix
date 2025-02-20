package com.sme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CIFDTO {
    private Long id;
    private String name;
    private String nrcNumber;
    private LocalDate dob;
    private String gender;
    private String phoneNumber;
    private String email;
    private String address;
    private String maritalStatus;
    private String occupation;
    private String incomeSource;
    private LocalDateTime createdAt;
    private Long branchId;  // Reference Branch ID

    // Fields for image upload (Not stored in DB)
    private MultipartFile frontNrc;
    private MultipartFile backNrc;

    // Fields for returning URLs (Stored in DB)
    private String fNrcPhotoUrl;
    private String bNrcPhotoUrl;
}
