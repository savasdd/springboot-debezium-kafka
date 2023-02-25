package com.debezium.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnitDto {

    @JsonProperty("unit_id")
    private Long unitId;

    @JsonProperty("ust_unit_id")
    private Long ustUnitId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("kod")
    private String kod;

    @JsonProperty("version")
    private Long version;

}
