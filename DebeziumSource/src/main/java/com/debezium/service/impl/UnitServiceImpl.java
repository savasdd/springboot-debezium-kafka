package com.debezium.service.impl;

import com.debezium.model.Unit;
import com.debezium.repository.ParameterRepository;
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
    private final ParameterRepository parameter;

    public List<Unit> getAll(){
        return repository.findAll();
    }

    public void create(Unit dto){
        dto.setVersion(0L);
        dto.setParameter(dto.getParameter().getId()!=null?parameter.findById(dto.getParameter().getId()).get():null);
        var model=repository.save(dto);
        log.info("create unit {}",model.getUnitId());
    }

    public void update(Long id ,Unit dto){
        var units=repository.findById(id);
        var newUnit=units.map(val->{
            val.setUstUnitId(dto.getUstUnitId());
            val.setName(dto.getName());
            val.setKod(dto.getKod());
            val.setParameter(dto.getParameter().getId()!=null?parameter.findById(dto.getParameter().getId()).get():null);
            return val;
        });
        var model=repository.save(newUnit.get());
        log.info("update unit {}",model.getUnitId());
    }

    public void delete(Long id){
        repository.deleteById(id);
        log.info("delete unit {}",id);
    }
}
