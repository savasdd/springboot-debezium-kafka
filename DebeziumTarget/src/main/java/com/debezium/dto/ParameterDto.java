package com.debezium.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParameterDto {

    @JsonProperty("id")
    private Long id;

}
