package com.sme.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "collateral")
public class Collateral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "collateral_type", nullable = false, length = 70)
    private String collateralType;

    @Column(name = "value", nullable = false, precision = 15, scale = 2)
    private BigDecimal value;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(nullable = false, length = 45)
    private Integer status;


    public Status getStatus() {
        return Status.fromCode(this.status);
    }

    public void setStatus(Status status) {
        this.status = status.getCode();
    }

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @Column(name = "collateral_code", nullable = false, length = 45, unique = true)
    private String collateralCode;

    @ManyToOne
    @JoinColumn(name = "cif_id", nullable = false)
    private CIF cif;



}
