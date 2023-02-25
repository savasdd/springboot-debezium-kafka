package com.debezium.service.impl;

import com.debezium.model.Personel;
import com.debezium.model.Unit;
import com.debezium.repository.PersonelRepository;
import com.debezium.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PersonelServiceImpl {

    private final PersonelRepository repository;
    private final UnitRepository unitRepository;

    public List<Personel> getAll(){
        return repository.findAll();
    }

    public void create(Personel dto){
        dto.setVersion(0L);
        if(dto.getUnit().getUnitId()!=null){
            var unit=unitRepository.findById(dto.getUnit().getUnitId());
            dto.setUnit(unit.isPresent()?unit.get():null);
        }
        var model=repository.save(dto);
        log.info("create Personel {}",model);
    }

    public void update(UUID id , Personel dto){
        var units=repository.findById(id);
        var newUnit=units.map(val->{
            val.setUnit(dto.getUnit());
            val.setName(dto.getName());
            val.setSurname(dto.getSurname());
            return val;
        });
        var model=repository.save(newUnit.get());
        log.info("update Personel {}",model);
    }

    public void delete(UUID id){
        repository.deleteById(id);
        log.info("create Personel {}",id);
    }
}
