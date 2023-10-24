package com.flaviopessini.springbootapigateway.services;

import com.flaviopessini.springbootapigateway.controllers.PersonController;
import com.flaviopessini.springbootapigateway.dtos.v1.PersonDTO;
import com.flaviopessini.springbootapigateway.dtos.v2.PersonDTOV2;
import com.flaviopessini.springbootapigateway.exceptions.ResourceNotFoundException;
import com.flaviopessini.springbootapigateway.mapper.Mapper;
import com.flaviopessini.springbootapigateway.mapper.custom.PersonMapper;
import com.flaviopessini.springbootapigateway.models.Person;
import com.flaviopessini.springbootapigateway.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();

    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonMapper personMapper;

    public List<PersonDTO> findAll() {
        logger.info("Finding all people!");
        final var persons = this.personRepository.findAll();
        final var dtos = Mapper.parseListObjects(persons, PersonDTO.class);
        dtos.forEach(x -> x.add(linkTo(methodOn(PersonController.class).findById(x.getKey())).withSelfRel()));
        return dtos;
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one person!");
        final var p = this.personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
        final var dto = Mapper.parseObject(p, PersonDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return dto;
    }

    public PersonDTO create(PersonDTO p) {
        logger.info("Creating one person!");
        final var person = Mapper.parseObject(p, Person.class);
        final var dto = Mapper.parseObject(this.personRepository.save(person), PersonDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }

    public PersonDTOV2 createV2(PersonDTOV2 p) {
        logger.info("Creating one person with v2!");
        final var person = personMapper.convertDTOToEntity(p);
        return personMapper.convertEntityToDTO(this.personRepository.save(person));
    }

    public PersonDTO update(PersonDTO p) {
        logger.info("Updating one person!");
        final var person = Mapper.parseObject(p, Person.class);
        final var update = this.personRepository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
        update.setFirstName(person.getFirstName());
        update.setLastName(person.getLastName());
        update.setAddress(person.getAddress());
        update.setGender(person.getGender());
        final var dto = Mapper.parseObject(this.personRepository.save(update), PersonDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");
        final var p = this.personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
        this.personRepository.delete(p);
    }
}
