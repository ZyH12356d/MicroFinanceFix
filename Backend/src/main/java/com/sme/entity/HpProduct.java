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

<<<<<<< Updated upstream
    @Column(name = "dealer_registration_id", nullable = false)
    private int dealerRegistrationId;
=======
    @ManyToOne
    @JoinColumn(name = "dealer_registration_id", nullable = false)
    private DealerRegistration dealerRegistration; // ✅ Corrected mapping
>>>>>>> Stashed changes

    @Column(name = "hp_registration_id", nullable = false)
    private int hpRegistrationId;

<<<<<<< Updated upstream
    @Column(name = "comission_fee")
=======

    @Column(name = "commission_fee", precision = 10, scale = 2, nullable = false)
>>>>>>> Stashed changes
    private BigDecimal commissionFee;

}
