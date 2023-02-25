package com.debezium.service.impl;

import com.debezium.model.Unit;
import com.debezium.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UnitServiceImpl {

    private final UnitRepository repository;

    public List<Unit> getAll(){
        return repository.findAll();
    }

    public void create(Unit dto){
        dto.setVersion(0L);
        var model=repository.save(dto);
        log.info("create unit {}",model);
    }

    public void update(Long id ,Unit dto){
        var units=repository.findById(id);
        var newUnit=units.map(val->{
            val.setUstUnitId(dto.getUstUnitId());
            val.setName(dto.getName());
            val.setKod(dto.getKod());
            return val;
        });
        var model=repository.save(newUnit.get());
        log.info("update unit {}",model);
    }

    public void delete(Long id){
        repository.deleteById(id);
        log.info("delete unit {}",id);
    }
}
