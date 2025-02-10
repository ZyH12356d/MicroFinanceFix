package com.sme.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "current_account")
public class CurrentAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "account_number", nullable = false, length = 45)
    private String accountNumber;

    @Column(nullable = false, length = 45)
    private String balance;

    @Column(name = "account_status", nullable = false, length = 45)
    private Integer status;

    public Status getStatus() {
        return Status.fromCode(this.status);
    }

    public void setStatus(Status status) {
        this.status = status.getCode();
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    @Column(name = "hold_amount", precision = 18, scale = 2)
    private BigDecimal holdAmount;

    @OneToOne
    @JoinColumn(name = "cif_id", referencedColumnName = "id")
    private CIF cif;

    @OneToMany(mappedBy = "currentAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountTransaction> transactions;

}
