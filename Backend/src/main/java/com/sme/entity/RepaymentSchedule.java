package com.sme.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Table(name = "repayment_schedule")
public class RepaymentSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    @Column(name = "interest_amount", nullable = false)
    private BigDecimal interestAmount;

    @Column(name = "principal_amount", nullable = false)
    private BigDecimal principalAmount;

    @Column(name = "late_fee", nullable = false)
    private BigDecimal lateFee;

    @Column(name = "interest_over_due", nullable = false)
    private BigDecimal interestOverDue;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "remaining_principal", nullable = false)
    private BigDecimal remainingPrincipal;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "paid_lat")
    private String paidLat;

    @Column(name = "late_fee_paid_date")
    private Timestamp lateFeePaidDate;

    @ManyToOne
    @JoinColumn(name = "sme_loan_id", nullable = false)
    private SmeLoanRegistration smeLoan;
}
