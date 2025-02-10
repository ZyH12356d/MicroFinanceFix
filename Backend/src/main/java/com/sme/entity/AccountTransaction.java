package com.sme.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "account_transaction")
public class AccountTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "transaction_type", nullable = false, length = 45)
    private String transactionType;

    @Column(nullable = false, length = 45)
    private String amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "transaction_date", nullable = false)
    private Date transactionDate;

    @Column(name = "account_transaction_desc", nullable = false, length = 45)
    private String transactionDescription;

    @Column(name = "transaction_status", nullable = false)
    private Integer status;


    public Status getStatus() {
        return Status.fromCode(this.status);
    }

    public void setStatus(Status status) {
        this.status = status.getCode();
    }

    @Column(name = "balance_after_transaction", precision = 15, scale = 2, nullable = false)
    private BigDecimal balanceAfterTransaction;

    @ManyToOne
    @JoinColumn(name = "current_account_id", nullable = false)
    private CurrentAccount currentAccount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
