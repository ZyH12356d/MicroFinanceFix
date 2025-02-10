package com.sme.dto;

import com.sme.entity.Status;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CurrentAccountDTO {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private Integer status;

    public Status getStatus() {
        return Status.fromCode(this.status);
    }

    public void setStatus(Status status) {
        this.status = status.getCode();
    }
    private Date dateCreated;
    private BigDecimal holdAmount;
    private Long cifId;
}

