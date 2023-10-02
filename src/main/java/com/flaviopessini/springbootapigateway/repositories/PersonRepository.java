package com.flaviopessini.springbootapigateway.repositories;

import com.flaviopessini.springbootapigateway.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
