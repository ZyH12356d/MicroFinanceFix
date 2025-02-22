package com.sme.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import com.sme.entity.Collateral;

@Entity
@Table(name = "collateral_type")
@Data
public class CollateralType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "collateralType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Collateral> collaterals;

}
