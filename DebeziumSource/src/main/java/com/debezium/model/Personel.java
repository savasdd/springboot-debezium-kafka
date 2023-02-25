package com.debezium.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "DEBEZIUM_PERSONEL")
public class Personel {

    @Id
    @Column(name = "PERSONEL_ID")
    @GeneratedValue(generator = "UUID")
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID personelId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNIT", referencedColumnName = "UNIT_ID", nullable = false)
    @JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
    private Unit unit;

    @Version
    @Column(name = "VERSION")
    private Long version;

}
