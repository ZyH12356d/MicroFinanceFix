package com.sme.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "hp_registration")
public class HpRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "hp_number", nullable = false)
    private String hpNumber;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "loan_amount")
    private BigDecimal loanAmount;

    @Column(name = "down_payment")
    private BigDecimal downPayment;

    @Column(name = "loan_term")
    private Integer loanTerm;

    @Column(name = "interest_rate")
    private String interestRate;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "status")
    private Integer status;


    public Status getStatus() {
        return Status.fromCode(this.status);
    }

    public void setStatus(Status status) {
        this.status = status.getCode();
    }

    @Column(name = "disbursement_id", nullable = false)
    private int disbursementId;

    @Column(name = "current_account_id", nullable = false)
    private int currentAccountId;

    @Column(name = "disbusement_date")
    private LocalDateTime disbursementDate;

}
