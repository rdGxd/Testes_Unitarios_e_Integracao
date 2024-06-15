package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.Person;
import com.example.demo.repositories.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class PersonServicesTest {

  @Mock
  private PersonRepository repository;

  @InjectMocks
  private PersonServices services;

  private Person person0;

  @BeforeEach
  public void setup() {
    // Given / Arrange
    person0 = new Person(
        1L,
        "Leandro",
        "Costa",
        "leandro@erudio.com.br",
        "Uberlândia - Minas Gerais - Brasil",
        "Male");
  }

  @DisplayName("JUnit test for Given Person Object when Save Person then Return Person Object")
  @Test
  void testGivenPersonObject_WhenSavePerson_thenReturnPersonObject() {

    // Given / Arrange
    given(repository.findByEmail(anyString())).willReturn(Optional.empty());
    given(repository.save(person0)).willReturn(person0);

    // When / Act
    Person savedPerson = services.create(person0);

    // Then / Assert
    assertNotNull(savedPerson);
    assertEquals("Leandro", savedPerson.getFirstName());
  }

  @DisplayName("JUnit test for Given Existing Email when Save Person then throws Exception")
  @Test
  void testGivenExistingEmail_WhenSavePerson_thenThrowsException() {

    // Given / Arrange
    given(repository.findByEmail(anyString())).willReturn(Optional.of(person0));

    // When / Act
    assertThrows(ResourceNotFoundException.class, () -> {
      services.create(person0);
    });

    // Then / Assert
    verify(repository, never()).save(any(Person.class));
  }

  @DisplayName("JUnit test for Given Persons List when Find All Persons then Return Persons List")
  @Test
  void testGivenPersonsList_WhenFindAllPersons_thenReturnPersonsList() {

    // Given / Arrange
    Person person1 = new Person("Leandro", "Santos", "Salvador - BA", "Male", "rdg@rdg.com");
    given(repository.findAll()).willReturn(List.of(person0, person1));

    // When / Act
    List<Person> persons = services.findAll();

    // Then / Assert
    assertNotNull(persons);
    assertEquals(2, persons.size());
  }

  @DisplayName("JUnit test for Given Empty Persons List when Find All Persons then Return Empty Persons List")
  @Test
  void testGivenEmptyPersonsList_WhenFindAllPersons_thenReturnEmptyPersonsList() {

    // Given / Arrange
    given(repository.findAll()).willReturn(Collections.emptyList());

    // When / Act
    List<Person> persons = services.findAll();

    // Then / Assert
    assertTrue(persons.isEmpty());
    assertEquals(0, persons.size());
  }

  @DisplayName("JUnit test for Given Person Id when Find By Id then Return Person Object")
  @Test
  void testGivenPersonId_WhenFindById_thenReturnPersonObject() {

    // Given / Arrange
    given(repository.findById(anyLong())).willReturn(Optional.of(person0));

    // When / Act
    Person savedPerson = services.findById(1L);

    // Then / Assert
    assertNotNull(savedPerson);
    assertEquals("Leandro", savedPerson.getFirstName());
  }

  @DisplayName("JUnit test for Given Nonexistent Person Id when Find By Id then throws Exception")
  @Test
  void testGivenPersonObject_WhenUpdatePerson_thenReturnUpdatedPersonObject() {

    // Given / Arrange
    given(repository.findById(anyLong())).willReturn(Optional.of(person0));
    person0.setFirstName("Rodrigo");
    given(repository.save(person0)).willReturn(person0);

    // When / Act
    Person updatedPerson = services.update(person0);

    // Then / Assert
    assertNotNull(updatedPerson);
    assertEquals("Rodrigo", updatedPerson.getFirstName());
  }

  @DisplayName("JUnit test for Given Person ID when Delete Person then Do Nothing")
  @Test
  void testGivenPersonID_WhenDeletePerson_thenDoNothing() {

    // Given / Arrange
    given(repository.findById(anyLong())).willReturn(Optional.of(person0));
    willDoNothing().given(repository).delete(person0);

    // When / Act
    services.delete(person0.getId());

    // Then / Assert
    verify(repository, times(1)).delete(person0);
  }
}
