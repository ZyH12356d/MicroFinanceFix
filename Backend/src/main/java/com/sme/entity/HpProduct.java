package com.sme.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "hp_product")
public class HpProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Integer status;


    public Status getStatus() {
        return Status.fromCode(this.status);
    }

    public void setStatus(Status status) {
        this.status = status.getCode();
    }

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "product_type_id", nullable = false)
    private int productTypeId;

    @Column(name = "dealer_registration_id", nullable = false)
    private int dealerRegistrationId;

    @Column(name = "hp_registration_id", nullable = false)
    private int hpRegistrationId;

    @Column(name = "comission_fee")
    private BigDecimal commissionFee;

}
