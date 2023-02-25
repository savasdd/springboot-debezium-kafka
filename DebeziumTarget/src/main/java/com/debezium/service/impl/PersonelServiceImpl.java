package com.debezium.service.impl;

import com.debezium.dto.PersonelDto;
import com.debezium.model.Personel;
import com.debezium.model.Unit;
import com.debezium.repository.PersonelRepository;
import com.debezium.repository.UnitRepository;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.data.Envelope;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PersonelServiceImpl {

    private final PersonelRepository repository;
    private final UnitRepository unitRepository;

    public void replicateData(Map<String, Object> data, Envelope.Operation operation) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        final PersonelDto dto = mapper.convertValue(data, PersonelDto.class);

        if (Envelope.Operation.DELETE == operation) {
            delete(dto.getPersonelId());
        } else {
            var exits=repository.findById(dto.getPersonelId()!=null?dto.getPersonelId():UUID.fromString("-1"));
            if(exits.isPresent()){
                update(dto,exits.get());
            }else{
                insert(dataMapDto(dto));
            }
        }
    }

    public void insert(Personel dto){
        dto.setVersion(0L);
        repository.save(dto);
    }

    private void update(PersonelDto dto,Personel model){
        model.setName(dto.getName());
        model.setSurname(dto.getSurname());
        model.setUnit(dto.getUnit().getUnitId()!=null?unitRepository.findById(dto.getUnit().getUnitId()).get():null);
        repository.save(model);
    }

    public void delete(UUID id){
        if(repository.existsById(id))
            repository.deleteById(id);
    }

    private Personel dataMapDto(PersonelDto dto){
        return Personel.builder().personelId(dto.getPersonelId()).name(dto.getName())
                .surname(dto.getSurname())
                .unit(dto.getUnit().getUnitId()!=null?unitRepository.findById(dto.getUnit().getUnitId()).get():null)
                .build();
    }

}
