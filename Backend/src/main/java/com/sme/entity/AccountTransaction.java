package com.sme.entity;

import com.sme.annotation.StatusConverter;
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
    private Long id;

    @Column(name = "transaction_type", nullable = false, length = 45)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // CREDIT or DEBIT


    @Column(nullable = false, length = 45)
    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "transaction_date", nullable = false)
    private Date transactionDate;

    @Column(name = "account_transaction_desc", nullable = false, length = 45)
    private String transactionDescription;

    @StatusConverter
    @Column(name = "transaction_status", nullable = false)
    private Integer status;


    @Column(name = "balance_after_transaction", precision = 15, scale = 2, nullable = false)
    private BigDecimal balanceAfterTransaction;

    @ManyToOne
    @JoinColumn(name = "current_account_id", nullable = false)
    private CurrentAccount currentAccount;


}
