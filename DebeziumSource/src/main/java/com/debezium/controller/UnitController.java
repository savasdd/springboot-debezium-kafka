package com.debezium.controller;

import com.debezium.model.Unit;
import com.debezium.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class UnitController {

    @Autowired
    private UnitService service;

    @GetMapping(value = "/units")
    public ResponseEntity<List<Unit>> getAll(){
        return new ResponseEntity<>(service.getUnitService().getAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/units")
    public ResponseEntity<?> create(@RequestBody Unit dto){
        service.getUnitService().create(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/units/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody Unit dto){
        service.getUnitService().update(id,dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/units/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        service.getUnitService().delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
