package com.debezium.controller;

import com.debezium.model.Personel;
import com.debezium.service.PersonelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class PersonelController {

    @Autowired
    private PersonelService service;

    @GetMapping(value = "/personels")
    public ResponseEntity<List<Personel>> getAll(){
        return new ResponseEntity<>(service.getPersonelService().getAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/personels")
    public ResponseEntity<?> create(@RequestBody Personel dto){
        service.getPersonelService().create(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/personels/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Personel dto){
        service.getPersonelService().update(id,dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/personels/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        service.getPersonelService().delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
