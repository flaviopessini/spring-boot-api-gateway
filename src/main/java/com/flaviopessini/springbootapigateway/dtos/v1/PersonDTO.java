package com.flaviopessini.springbootapigateway.dtos.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Getter
@Setter
@JsonPropertyOrder({"id", "firstName", "lastName", "gender", "address"})
public class PersonDTO extends RepresentationModel<PersonDTO> implements Serializable {
    @JsonProperty("id")
    private Long key;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
}
