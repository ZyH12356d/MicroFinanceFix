package com.sme.dto;

import com.sme.entity.Status;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class HpProductDTO {
    private Long id;
    private String name;
    private Integer status;
    private BigDecimal price;
    private Long productTypeId;
    private Long dealerRegistrationId;
<<<<<<< Updated upstream
    private BigDecimal commissionFee; // ✅ Ensure this is included
=======
    private BigDecimal commissionFee;
>>>>>>> Stashed changes

    public Status getStatus() {
        return Status.fromCode(this.status);
    }

    public void setStatus(Status status) {
        this.status = status.getCode();
    }
}
