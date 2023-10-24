package com.flaviopessini.springbootapigateway.dtos.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@JsonPropertyOrder({"id", "author", "launchDate", "price", "title"})
public class BookDTO extends RepresentationModel<BookDTO> implements Serializable {
    @JsonProperty("id")
    private Long key;
    private String author;
    private Date launchDate;
    private Double price;
    private String title;
}
