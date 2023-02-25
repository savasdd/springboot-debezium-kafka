package com.debezium.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PEFORMANS_PERSONEL")
public class Personel {

    @Id
    @Column(name = "PERSONEL_ID")
    private UUID personelId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNIT", referencedColumnName = "UNIT_ID", nullable = false)
    @JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATE_CODE", referencedColumnName = "ID")
    @JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
    private Parameter parameter;

    @Version
    @Column(name = "VERSION")
    private Long version;

}
