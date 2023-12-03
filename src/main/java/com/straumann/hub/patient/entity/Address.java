package com.straumann.hub.patient.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "address")
public class Address {

    @Id
    @Column(name = "addr_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addrId;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "zip")
    private Integer zip;

    @Column(name = "street")
    private String street;

}
