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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    PagedResourcesAssembler<PersonDTO> assembler;

    public PagedModel<EntityModel<PersonDTO>> findAll(PageRequest pageable) {
        logger.info("Finding all people!");
        final var persons = this.personRepository.findAll(pageable);
        final var personDTOPage = persons.map(p -> Mapper.parseObject(p, PersonDTO.class));
        personDTOPage.map(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
        final var link = linkTo(methodOn(PersonController.class).all(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
        return assembler.toModel(personDTOPage, link);
    }

    public PagedModel<EntityModel<PersonDTO>> findPersonsByName(String firstName, PageRequest pageable) {
        logger.info("Finding all people by firstName!");
        final var persons = this.personRepository.findPersonsByName(firstName, pageable);
        final var personDTOPage = persons.map(p -> Mapper.parseObject(p, PersonDTO.class));
        personDTOPage.map(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
        final var link = linkTo(methodOn(PersonController.class).all(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
        return assembler.toModel(personDTOPage, link);
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

    @Transactional
    public PersonDTO disablePerson(Long id) {
        logger.info("Disabling one person!");
        this.personRepository.disablePerson(id);
        final var p = this.personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
        final var dto = Mapper.parseObject(p, PersonDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return dto;
    }
}
