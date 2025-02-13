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
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status")
    private Integer status;

    public Status getStatus() {
        return Status.fromCode(this.status);
    }

    public void setStatus(Status status) {
        this.status = status.getCode();
    }

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "product_type_id", nullable = false)
    private ProductType productType;

    @ManyToOne
    @JoinColumn(name = "dealer_registration_id", nullable = false)
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    private DealerRegistration dealerRegistration; // ✅ Corrected mapping

    @Column(name = "hp_registration_id", nullable = false)
    private int hpRegistrationId;

    @Column(name = "comission_fee", precision = 10, scale = 2)
=======
    private DealerRegistration dealerRegistration;

    @Column(name = "commission_fee", precision = 10, scale = 2, nullable = false)
>>>>>>> Stashed changes
=======
    private DealerRegistration dealerRegistration;

    @Column(name = "commission_fee", precision = 10, scale = 2, nullable = false)
>>>>>>> Stashed changes
    private BigDecimal commissionFee;
}
