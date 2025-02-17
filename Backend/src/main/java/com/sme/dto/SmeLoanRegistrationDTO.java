package com.sme.dto;

import com.sme.annotation.StatusConverter;
import com.sme.entity.Status;
import lombok.Data;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
public class SmeLoanRegistrationDTO {
    private Long id;
    private BigDecimal loanAmount;
    private BigDecimal interestRate;
    private Integer gracePeriod;
    private Long repaymentDuration;
    private BigDecimal documentFee;
    private BigDecimal serviceCharges;
    @StatusConverter
    private Integer status;
    private Long currentAccountId;
    private Long collateralId;
    private LocalDateTime dueDate;
    private LocalDateTime repaymentStartDate;
    private Date disbursementDate;




}
