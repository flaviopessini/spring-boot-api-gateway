package com.flaviopessini.springbootapigateway.unittests.mapper;

import com.flaviopessini.springbootapigateway.dtos.v1.PersonDTO;
import com.flaviopessini.springbootapigateway.mapper.Mapper;
import com.flaviopessini.springbootapigateway.models.Person;
import com.flaviopessini.springbootapigateway.unittests.mapper.mocks.MockPersonTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ModelMapperTest {

    private MockPersonTest input;

    @BeforeEach
    public void setUp() {
        input = new MockPersonTest();
    }

    @Test
    public void parseEntityToDTO() {
        final var output = Mapper.parseObject(input.mockEntity(), PersonDTO.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First name 0", output.getFirstName());
        assertEquals("Last name 0", output.getLastName());
        assertEquals("Address 0", output.getAddress());
        assertEquals("Male", output.getGender());
    }

    @Test
    public void parseEntityListToDTOList() {
        final var output = Mapper.parseListObjects(input.mockEntityList(), PersonDTO.class);
        final var zero = output.get(0);

        assertEquals(Long.valueOf(0L), zero.getId());
        assertEquals("First name 0", zero.getFirstName());
        assertEquals("Last name 0", zero.getLastName());
        assertEquals("Address 0", zero.getAddress());
        assertEquals("Male", zero.getGender());

        final var seven = output.get(7);

        assertEquals(Long.valueOf(7L), seven.getId());
        assertEquals("First name 7", seven.getFirstName());
        assertEquals("Last name 7", seven.getLastName());
        assertEquals("Address 7", seven.getAddress());
        assertEquals("Female", seven.getGender());
    }

    @Test
    public void parseDTOToEntity() {
        final var output = Mapper.parseObject(input.mockDTO(), Person.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First name 0", output.getFirstName());
        assertEquals("Last name 0", output.getLastName());
        assertEquals("Address 0", output.getAddress());
        assertEquals("Male", output.getGender());
    }

    @Test
    public void parseDTOListToEntityList() {
        final var output = Mapper.parseListObjects(input.mockDTOList(), Person.class);
        final var zero = output.get(0);

        assertEquals(Long.valueOf(0L), zero.getId());
        assertEquals("First name 0", zero.getFirstName());
        assertEquals("Last name 0", zero.getLastName());
        assertEquals("Address 0", zero.getAddress());
        assertEquals("Male", zero.getGender());

        final var seven = output.get(7);

        assertEquals(Long.valueOf(7L), seven.getId());
        assertEquals("First name 7", seven.getFirstName());
        assertEquals("Last name 7", seven.getLastName());
        assertEquals("Address 7", seven.getAddress());
        assertEquals("Female", seven.getGender());
    }
}