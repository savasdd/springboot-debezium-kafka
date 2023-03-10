package com.debezium.service.impl;

import com.debezium.dto.UnitDto;
import com.debezium.model.Unit;
import com.debezium.repository.ParameterRepository;
import com.debezium.repository.UnitRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.data.Envelope;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UnitServiceImpl {

    private final UnitRepository repository;
    private final ParameterRepository parameter;

    public void replicateData(Map<String, Object> data, Envelope.Operation operation) {
        final ObjectMapper mapper = new ObjectMapper();
         mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        final UnitDto dto = mapper.convertValue(data, UnitDto.class);

        if (Envelope.Operation.DELETE == operation) {
           delete(dto.getUnitId());
        } else {
            var unit=repository.findById(dto.getUnitId()!=null?dto.getUnitId():-1L);
            if(unit.isPresent()) {
                update(dto, unit.get());
            }else {
                insert(dataMapDto(dto));
            }
        }
    }


    public void insert(Unit dto){
        dto.setVersion(0L);
        var concat=dto.getUnitId().toString()+"_"+(dto.getUstUnitId()!=null?dto.getUstUnitId().toString():"0");
        dto.setConcat(concat);
        var model=repository.save(dto);
    }

    public void update(UnitDto dto, Unit model){
        var concat=dto.getUnitId().toString()+"_"+(dto.getUstUnitId()!=null?dto.getUstUnitId().toString():"0");
        model.setUstUnitId(dto.getUstUnitId());
        model.setName(dto.getName());
        model.setKod(dto.getKod());
        model.setConcat(concat);
        model.setValidCode((dto.getValidCode()!=null && dto.getValidCode().getId()!=null)?parameter.findById(dto.getValidCode().getId()).get():null);
        repository.save(model);
    }

    public void delete(Long id){
        if(repository.existsById(id))
            repository.deleteById(id);
    }

    private Unit dataMapDto(UnitDto dto){
        return Unit.builder().unitId(dto.getUnitId()).ustUnitId(dto.getUstUnitId())
                .name(dto.getName()).kod(dto.getKod())
                .validCode((dto.getValidCode()!=null && dto.getValidCode().getId()!=null)?parameter.findById(dto.getValidCode().getId()).get():null).build();
    }
}
