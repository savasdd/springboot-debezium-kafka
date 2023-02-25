package com.debezium.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PEFORMANS_UNIT")
public class Unit {

    @Id
    @Column(name = "UNIT_ID")
    private Long unitId;

    @Column(name = "UST_UNIT_ID")
    private Long ustUnitId;

    @Column(name = "CONCAT")
    private String concat;

    @Column(name = "NAME")
    private String name;

    @Column(name = "KOD")
    private String kod;

    @Version
    @Column(name = "VERSION")
    private Long version;

}
