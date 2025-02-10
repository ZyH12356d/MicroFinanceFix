package com.sme.dto;

import com.sme.entity.Status;
import lombok.Data;
import com.sme.dto.AddressDTO;
import java.util.Date;

@Data
public class BranchDTO {
    private Long id;
    private String name;
    private String branchCode;
    private String phoneNumber;
    private String email;
    private Date createdDate;
    private Date updatedDate;
    private Integer status;
    private AddressDTO address;


    public Status getStatus() {
        return Status.fromCode(this.status);
    }

    public void setStatus(Status status) {
        this.status = status.getCode();
    }
}
