package com.sme.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private Long id;
    private String city;
    private String town;
    private String street;
}
