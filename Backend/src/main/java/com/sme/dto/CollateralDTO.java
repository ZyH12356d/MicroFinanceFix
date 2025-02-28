package com.sme.dto;

import com.sme.annotation.StatusConverter;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CollateralDTO {
    private Long id;
    private String collateralTypeId;
    private BigDecimal value;
    private String description;
    @StatusConverter
    private Integer status;
    private Date date;
    private String collateralCode;
    private Long cifId;
}
