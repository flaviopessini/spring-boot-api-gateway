package com.flaviopessini.springbootapigateway.services;

import com.flaviopessini.springbootapigateway.dtos.v1.PersonDTO;
import com.flaviopessini.springbootapigateway.exceptions.ResourceNotFoundException;
import com.flaviopessini.springbootapigateway.mapper.Mapper;
import com.flaviopessini.springbootapigateway.models.Person;
import com.flaviopessini.springbootapigateway.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();

    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository personRepository;

    public List<PersonDTO> findAll() {
        logger.info("Finding all people!");
        final var persons = this.personRepository.findAll();
        return Mapper.parseListObjects(persons, PersonDTO.class);
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one person!");
        final var p = this.personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
        return Mapper.parseObject(p, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO p) {
        logger.info("Creating one person!");
        final var person = Mapper.parseObject(p, Person.class);
        return Mapper.parseObject(this.personRepository.save(person), PersonDTO.class);
    }

    public PersonDTO update(PersonDTO p) {
        logger.info("Updating one person!");
        final var person = Mapper.parseObject(p, Person.class);
        final var update = this.personRepository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
        update.setFirstName(person.getFirstName());
        update.setLastName(person.getLastName());
        update.setAddress(person.getAddress());
        update.setGender(person.getGender());
        return Mapper.parseObject(this.personRepository.save(update), PersonDTO.class);
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");
        final var p = this.personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
        this.personRepository.delete(p);
    }
}
