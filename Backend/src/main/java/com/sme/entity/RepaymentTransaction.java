package com.sme.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

import java.math.BigDecimal;

@Entity
@Table(name = "repayment_transaction")
public class RepaymentTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "payment_date", nullable = false)
    private Timestamp paymentDate;

    @Column(name = "paid_principal", nullable = false)
    private BigDecimal paidPrincipal;

    @Column(name = "paid_interest", nullable = false)
    private BigDecimal paidInterest;

    @Column(name = "paid_late_fee", nullable = false)
    private BigDecimal paidLateFee;

    @Column(name = "paid_IOD", nullable = false)
    private BigDecimal paidIOD;

    @Column(name = "remaining_principal", nullable = false)
    private BigDecimal remainingPrincipal;

    @Column(name = "status", nullable = false)
    private Integer status;


    public Status getStatus() {
        return Status.fromCode(this.status);
    }

    public void setStatus(Status status) {
        this.status = status.getCode();
    }

    @ManyToOne
    @JoinColumn(name = "current_account_id", nullable = false)
    private CurrentAccount currentAccount;

    @ManyToOne
    @JoinColumn(name = "repayment_schedule_id", nullable = false)
    private RepaymentSchedule repaymentSchedule;
}
