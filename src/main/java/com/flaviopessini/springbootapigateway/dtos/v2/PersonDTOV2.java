package com.flaviopessini.springbootapigateway.dtos.v2;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class PersonDTOV2 implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String address;
    private String gender;
}
