package com.flaviopessini.springbootapigateway.services;

import com.flaviopessini.springbootapigateway.controllers.PersonController;
import com.flaviopessini.springbootapigateway.dtos.v1.BookDTO;
import com.flaviopessini.springbootapigateway.exceptions.ResourceNotFoundException;
import com.flaviopessini.springbootapigateway.mapper.Mapper;
import com.flaviopessini.springbootapigateway.models.Book;
import com.flaviopessini.springbootapigateway.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

    private final AtomicLong counter = new AtomicLong();

    private final Logger logger = Logger.getLogger(BookServices.class.getName());

    @Autowired
    BookRepository bookRepository;

    public List<BookDTO> findAll() {
        logger.info("Finding all books!");
        final var persons = this.bookRepository.findAll();
        final var dtos = Mapper.parseListObjects(persons, BookDTO.class);
        dtos.forEach(x -> x.add(linkTo(methodOn(PersonController.class).findById(x.getKey())).withSelfRel()));
        return dtos;
    }

    public BookDTO findById(Long id) {
        logger.info("Finding one book!");
        final var p = this.bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
        final var dto = Mapper.parseObject(p, BookDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return dto;
    }

    public BookDTO create(BookDTO p) {
        logger.info("Creating one book!");
        final var person = Mapper.parseObject(p, Book.class);
        final var dto = Mapper.parseObject(this.bookRepository.save(person), BookDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }

    public BookDTO update(BookDTO p) {
        logger.info("Updating one book!");
        final var book = Mapper.parseObject(p, Book.class);
        final var update = this.bookRepository.findById(book.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
        update.setAuthor(book.getAuthor());
        update.setLaunchDate(book.getLaunchDate());
        update.setPrice(book.getPrice());
        update.setTitle(book.getTitle());
        final var dto = Mapper.parseObject(this.bookRepository.save(update), BookDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting one book!");
        final var p = this.bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
        this.bookRepository.delete(p);
    }
}
