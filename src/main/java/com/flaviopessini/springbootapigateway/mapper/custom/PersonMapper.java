package com.flaviopessini.springbootapigateway.mapper.custom;

import com.flaviopessini.springbootapigateway.dtos.v2.PersonDTOV2;
import com.flaviopessini.springbootapigateway.models.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {

    public PersonDTOV2 convertEntityToDTO(Person p) {
        final var dto = new PersonDTOV2();
        dto.setId(p.getId());
        dto.setFirstName(p.getFirstName());
        dto.setLastName(p.getLastName());
        dto.setBirthday(new Date());
        dto.setGender(p.getGender());
        dto.setAddress(p.getAddress());
        return dto;
    }

    public Person convertDTOToEntity(PersonDTOV2 dto){
        final var person = new Person();
        person.setId(dto.getId());
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        //person.setBirthday(dto.getBirthday());
        person.setGender(dto.getGender());
        person.setAddress(dto.getAddress());
        return person;
    }
}
