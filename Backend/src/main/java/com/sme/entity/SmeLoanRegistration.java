package com.sme.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "sme_loan_registration")
public class SmeLoanRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "loan_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal loanAmount;

    @Column(name = "interest_rate", nullable = false, precision = 15, scale = 2)
    private BigDecimal interestRate;

    @Column(name = "grace_period", nullable = false)
    private Integer gracePeriod;

    @Column(name = "grace_start")
    private LocalDate graceStartDate;

    @Column(name = "grace_end")
    private LocalDate graceEndDate;

    @Column(name = "repayment_duration", nullable = false, length = 100)
    private int repaymentDuration;

    @Column(name = "document_fee", nullable = false)
    private BigDecimal documentFee;

    @Column(name = "service_charges", nullable = false)
    private BigDecimal serviceCharges;

    @Column(name = "status", nullable = false)
    private Integer status;


    public Status getStatus() {
        return Status.fromCode(this.status);
    }

    public void setStatus(Status status) {
        this.status = status.getCode();
    }

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "repayment_start_date", nullable = false)
    private LocalDateTime repaymentStartDate;

    @Column(name = "disbursement_date")
    private Date disbursementDate;

    @ManyToOne
    @JoinColumn(name = "current_account_id", nullable = false)
    private CurrentAccount currentAccount;

    @ManyToOne
    @JoinColumn(name = "collateral_id", nullable = false)
    private Collateral collateral;

    @OneToMany(mappedBy = "smeLoan", cascade = CascadeType.ALL)
    private List<RepaymentSchedule> repaymentSchedules;


}
