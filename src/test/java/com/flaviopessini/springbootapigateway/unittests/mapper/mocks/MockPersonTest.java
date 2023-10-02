package com.flaviopessini.springbootapigateway.unittests.mapper.mocks;

import com.flaviopessini.springbootapigateway.dtos.v1.PersonDTO;
import com.flaviopessini.springbootapigateway.models.Person;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class MockPersonTest {

    private final ModelMapper mapper = new ModelMapper();

    public Person mockEntity() {
        return mockEntity(0);
    }

    public PersonDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Person> mockEntityList() {
        final var list = new ArrayList<Person>();
        for (int i = 0; i < 14; i++) {
            list.add(mockEntity(i));
        }
        return list;
    }

    public List<PersonDTO> mockDTOList() {
        final var list = new ArrayList<PersonDTO>();
        for (int i = 0; i < 14; i++) {
            list.add(mockDTO(i));
        }
        return list;
    }

    public Person mockEntity(Integer n) {
        final var p = new Person();
        p.setId(n.longValue());
        p.setFirstName("First name " + n);
        p.setLastName("Last name " + n);
        p.setAddress("Address " + n);
        p.setGender(((n % 2) == 0) ? "Male" : "Female");
        return p;
    }

    public PersonDTO mockDTO(Integer n) {
        return mapper.map(mockEntity(n), PersonDTO.class);
    }

}
