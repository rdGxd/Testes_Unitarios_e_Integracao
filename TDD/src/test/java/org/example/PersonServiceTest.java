package org.example;

import org.example.model.Person;
import org.example.service.IPersonService;
import org.example.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {

  IPersonService serivce;
  Person person;

  @BeforeEach
  void setUp() {
    serivce = new PersonService();
    person = new Person("Keith", "Moon", "teste@test.com", "Salvador-BA", "Male");
  }

  // test[System Under Test]_[Condition or State Change]_[Expected Result],
  @DisplayName("When Create a Person with Success Should Contains Valid Fields in Returned Person Object")
  @Test
  void testCreatePerson_WhenSuccess_ShouldContainsValidFieldsInReturnedPersonObject() {

    // Given / Arrange

    // When / Act
    Person actual = serivce.createPerson(person);

    // Then / Assert
    assertNotNull(actual, "The createPerson() should not have returned null!");
    assertNotNull(person.getId(), "Person ID is Missing!");
    assertEquals(person.getFirstName(), actual.getFirstName(), "The Firstname is Incorrect!");
    assertEquals(person.getLastName(), actual.getLastName(), "The Lastname is Incorrect!");
    assertEquals(person.getEmail(), actual.getEmail(), "The Email is Incorrect!");
    assertEquals(person.getAddress(), actual.getAddress(), "The Address is Incorrect!");
    assertEquals(person.getGender(), actual.getGender(), "The Gender is Incorrect!");
  }

  // test[System Under Test]_[Condition or State Change]_[Expected Result],
  @DisplayName("When Create a Person with null e-mail Should throw Exception")
  @Test
  void testCreatePerson_WhitNullEmail_ShouldThrowIllegalArgumentException() {
    // Given / Arrange
    person.setEmail(null);
    String expectedMessage = "The Person email is null ou empty!";

    // When / Act & Then / Assert
    var msg = assertThrows(IllegalArgumentException.class, () -> serivce.createPerson(person), "Empty e-Mail should have cause an IllegalArgumentException");

    // Then / Assert
    assertEquals(expectedMessage, msg.getMessage(), "The Exception Message is Incorrect!");
  }
}
