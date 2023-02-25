package com.debezium.dto;

import com.debezium.model.Parameter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class PersonelDto {

    @JsonProperty("personel_id")
    private UUID personelId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("unit")
    private UnitPersDto unit;

    @JsonProperty("state_code")
    private ParameterDto stateCode;

    @JsonProperty("version")
    private Long version;

}
