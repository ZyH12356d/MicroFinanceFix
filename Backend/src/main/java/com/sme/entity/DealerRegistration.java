package com.sme.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.sql.Timestamp;

@Entity
@Data
@Table(name = "dealer_registration")
public class DealerRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "registration_date")
    private Timestamp registrationDate;

    @Column(name = "status")
    private Integer status;


    public Status getStatus() {
        return Status.fromCode(this.status);
    }

    public void setStatus(Status status) {
        this.status = status.getCode();
    }

    @Column(name = "address_id", nullable = false)
    private int addressId;

    @Column(name = "current_account_id", nullable = false)
    private int currentAccountId;

 }

