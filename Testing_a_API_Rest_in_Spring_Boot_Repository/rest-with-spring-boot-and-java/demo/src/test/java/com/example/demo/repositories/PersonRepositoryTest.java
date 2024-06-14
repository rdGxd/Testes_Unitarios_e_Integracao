package com.example.demo.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.Person;

@DataJpaTest
public class PersonRepositoryTest {

  Person person;

  @Autowired
  private PersonRepository repository;

  @BeforeEach
  void setUp() {
    // Given / Arrange
    person = new Person("Rodrigo", "Silva", "Salvador - BA", "Male", "rdg@rdg.com");
    repository.save(person);
  }

  @DisplayName("Given Person Object when save then Return Saved Person")
  @Test
  void testGivenPersonObject_WhenSave_thenReturnSavedPerson() {
    // Given / Arrange

    // When / Act
    Person savedPerson = person;

    // Then / Assert
    assertNotNull(savedPerson);
    assertTrue(savedPerson.getId() > 0);
  }

  @DisplayName("Given Person Object when find all then Return Person list")
  @Test
  void testGivenPersonObject_WhenFindAll_thenReturnPersonList() {
    // Given / Arrange
    Person person1 = new Person("Leandro", "Santos", "Salvador - BA", "Male", "rdg@rdg.com");
    repository.save(person1);

    // When / Act
    List<Person> personList = repository.findAll();

    // Then / Assert
    assertNotNull(personList);
    assertEquals(2, personList.size());
  }

  @DisplayName("Given Person Object when find by id then Return Person Object")
  @Test
  void testGivenPersonObject_WhenFindById_thenReturnPersonObject() {
    // Given / Arrange

    // When / Act
    Person savedPerson = repository.findById(person.getId()).get();
    // Then / Assert
    assertNotNull(savedPerson);
    assertEquals(person.getId(), savedPerson.getId());
  }

  @DisplayName("Given Person Object when find by Email then Return Person Object")
  @Test
  void testGivenPersonObject_WhenFindByEmail_thenReturnPersonObject() {
    // Given / Arrange

    // When / Act
    Person savedPerson = repository.findByEmail(person.getEmail()).get();
    // Then / Assert
    assertNotNull(savedPerson);
    assertEquals(person.getEmail(), savedPerson.getEmail());
  }

  @DisplayName("Given Person Object when update then Return Updated Person Object")
  @Test
  void testGivenPersonObject_WhenUpdatePerson_thenReturnUpdatedPersonObject() {
    // Given / Arrange

    // When / Act
    Person savedPerson = repository.findByEmail(person.getEmail()).get();
    savedPerson.setFirstName("Beatriz");
    Person updatedPerson = repository.save(savedPerson);
    // Then / Assert
    assertNotNull(updatedPerson);
    assertEquals("Beatriz", updatedPerson.getFirstName());
  }

  @DisplayName("Given Person Object when delete then Remove Person Object")
  @Test
  void testGivenPersonObject_WhenDelete_thenRemovePerson() {
    // Given / Arrange

    // When / Act
    repository.deleteById(person.getId());
    Optional<Person> savedPerson = repository.findById(person.getId());

    // Then / Assert
    assertTrue(savedPerson.isEmpty());
  }

  @DisplayName("Given Fist name and Last name when find JPQL then Return Person Object")
  @Test
  void testGivenFirstNameAndLastName_WhenFindByJPQL_thenReturnPersonObject() {
    // Given / Arrange
    String firstName = "Rodrigo";
    String lastName = "Silva";

    // When / Act
    Person savedPerson = repository.findByJPQL(firstName, lastName);

    // Then / Assert
    assertNotNull(savedPerson);
    assertEquals(firstName, savedPerson.getFirstName());
    assertEquals(lastName, savedPerson.getLastName());
  }

  @DisplayName("Given Fist name and Last name when find JPQL Named Parameters then Return Person Object")
  @Test
  void testGivenFirstNameAndLastName_WhenFindByJPQLNamedParameters_thenReturnPersonObject() {
    // Given / Arrange
    String firstName = "Rodrigo";
    String lastName = "Silva";

    // When / Act
    Person savedPerson = repository.findByJPQLNamedParameters(firstName, lastName);

    // Then / Assert
    assertNotNull(savedPerson);
    assertEquals(firstName, savedPerson.getFirstName());
    assertEquals(lastName, savedPerson.getLastName());
  }

  @DisplayName("Given Fist name and Last name when find JPQL Native Parameters then Return Person Object")
  @Test
  void testGivenFirstNameAndLastName_WhenFindByJPQLNative_thenReturnPersonObject() {
    // Given / Arrange
    String firstName = "Rodrigo";
    String lastName = "Silva";

    // When / Act
    Person savedPerson = repository.findByNativeSQL(firstName, lastName);

    // Then / Assert
    assertNotNull(savedPerson);
    assertEquals(firstName, savedPerson.getFirstName());
    assertEquals(lastName, savedPerson.getLastName());
  }

  @DisplayName("Given Fist name and Last name when find JPQL Native Parameters then Return Person Object")
  @Test
  void testGivenFirstNameAndLastName_WhenFindByJPQLNativeWithNamedParameters_thenReturnPersonObject() {
    // Given / Arrange
    String firstName = "Rodrigo";
    String lastName = "Silva";

    // When / Act
    Person savedPerson = repository.findByNativeSQLwithnamedParameters(firstName, lastName);

    // Then / Assert
    assertNotNull(savedPerson);
    assertEquals(firstName, savedPerson.getFirstName());
    assertEquals(lastName, savedPerson.getLastName());
  }
}
