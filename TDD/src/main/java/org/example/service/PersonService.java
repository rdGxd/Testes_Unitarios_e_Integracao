package org.example.service;

import org.example.model.Person;

import java.util.concurrent.atomic.AtomicLong;

public class PersonService implements IPersonService {

  @Override
  public Person createPerson(Person person) {
    if (person.getEmail() == null || person.getEmail().isBlank()) {
      throw new IllegalArgumentException("The Person email is null ou empty!");
    }

    long id = new AtomicLong().incrementAndGet();
    person.setId(id);
    return person;
  }
}
