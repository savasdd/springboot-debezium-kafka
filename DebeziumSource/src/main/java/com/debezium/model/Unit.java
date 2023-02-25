package com.debezium.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VALID_CODE", referencedColumnName = "ID")
    @JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
    private Parameter parameter;

    @Version
    @Column(name = "VERSION")
    private Long version;

}
