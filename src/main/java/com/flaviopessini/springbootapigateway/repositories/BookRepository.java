package com.flaviopessini.springbootapigateway.repositories;

import com.flaviopessini.springbootapigateway.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
