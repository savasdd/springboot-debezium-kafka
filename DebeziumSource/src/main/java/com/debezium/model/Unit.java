package com.debezium.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "DEBEZIUM_UNIT")
public class Unit {

    @Id
    @Column(name = "UNIT_ID",updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unitId;

    @Column(name = "UST_UNIT_ID")
    private Long ustUnitId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "KOD")
    private String kod;

    @Version
    @Column(name = "VERSION")
    private Long version;

}
