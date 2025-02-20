package com.sme.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Entity
@Data
@Table(name = "sme_loan_collateral")
public class SmeLoanCollateral implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private SmeLoanRegistration smeLoan;

    @ManyToOne
    @JoinColumn(name = "collateral_id", nullable = false)
    private Collateral collateral;
}
