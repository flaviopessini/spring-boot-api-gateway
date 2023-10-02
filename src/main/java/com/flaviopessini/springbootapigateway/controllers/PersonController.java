package com.flaviopessini.springbootapigateway.controllers;

import com.flaviopessini.springbootapigateway.dtos.v1.PersonDTO;
import com.flaviopessini.springbootapigateway.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/person")
public class PersonController {

    @Autowired
    private PersonServices personServices;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonDTO>> all() {
        return ResponseEntity.ok().body(this.personServices.findAll());
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDTO> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok().body(this.personServices.findById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO p) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.personServices.create(p));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDTO> update(@RequestBody PersonDTO p) {
        return ResponseEntity.accepted().body(this.personServices.update(p));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        this.personServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
