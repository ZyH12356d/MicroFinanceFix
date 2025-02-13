package com.sme.dto;

import com.sme.entity.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class CollateralDTO {
    private Long id;
    private String collateralType;
    private String value;
    private String description;
    private Integer status;
    private Date date;
    private String collateralCode;
    private Long cifId;

    public Status getStatus() {
        return Status.fromCode(this.status);
    }

    public void setStatus(Status status) {
        this.status = status.getCode();
    }
}
