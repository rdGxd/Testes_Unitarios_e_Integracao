package com.example.demo.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Person;
import com.example.demo.repositories.PersonRepository;

@Service
public class PersonService {
  private Logger logger = Logger.getLogger(PersonService.class.getName());

  @Autowired
  private PersonRepository repository;

  public List<Person> findAll() {
    logger.info("Finding all persons");
    return repository.findAll();
  }

  public Person findById(Long id) {
    logger.info("Finding one person");
    return repository.findById(id).orElseThrow(() -> new RuntimeException("Person not found"));
  }

  public Person create(Person person) {
    logger.info("Creating a person");
    return repository.save(person);
  }

  public Person update(Person person) {
    logger.info("Creating a person");
    Person p = findById(person.getId());
    p.setFirstName(person.getFirstName());
    p.setLastName(person.getLastName());
    p.setAddress(person.getAddress());
    p.setGender(person.getGender());
    repository.save(p);
    return person;
  }

  public void delete(Long id) {
    logger.info("Deleting a person");
    Person p = findById(id);
    repository.delete(p);
  }
}
