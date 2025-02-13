package com.sme.dto;

import com.sme.entity.Status;
import lombok.Data;

@Data
public class ProductTypeDTO {
    private Long id;
    private String name;
    private Integer status;

    public Status getStatus() {
        return Status.fromCode(this.status);
    }

    public void setStatus(Status status) {
        this.status = status.getCode();
    }
}
