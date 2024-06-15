package com.example.demo.integrationstests.controllers;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.config.TestConfig;
import com.example.demo.integrationstests.testcontainers.AbstractIntegrationTest;
import com.example.demo.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PersonControllerTest extends AbstractIntegrationTest {
  private static RequestSpecification specification;
  private static ObjectMapper mapper;
  private static Person person;

  @BeforeAll
  public static void setUp() {
    // Given / Arrange
    mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    specification = new RequestSpecBuilder()
        .setBasePath("/person")
        .setPort(TestConfig.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();

    person = new Person("Rodrigo", "Silva", "Salvador - BA", "Male", "rdg@rdg.com");
  }

  @Test
  @Order(1)
  @DisplayName("JUnit integration given person object when create one person should return a person object")
  void IntegrationTestGivenPersonObject_when_CreateOnePerson_ShouldReturnAPersonObject()
      throws JsonMappingException, JsonProcessingException {
    String content = given()
        .spec(specification)
        .contentType(TestConfig.CONTENT_TYPE_JSON)
        .body(person)
        .when()
        .post()
        .then()
        .statusCode(200)
        .extract()
        .body()
        .asString();

    Person createdPerson = mapper.readValue(content, Person.class);
    person = createdPerson;

    assertNotNull(createdPerson);
    assertNotNull(createdPerson.getId());
    assertNotNull(createdPerson.getFirstName());
    assertNotNull(createdPerson.getLastName());
    assertNotNull(createdPerson.getAddress());
    assertNotNull(createdPerson.getGender());
    assertNotNull(createdPerson.getEmail());

    assertTrue(createdPerson.getId() > 0);
    assertEquals("Rodrigo", createdPerson.getFirstName());
    assertEquals("Silva", createdPerson.getLastName());
    assertEquals("Salvador - BA", createdPerson.getAddress());
    assertEquals("Male", createdPerson.getGender());
    assertEquals("rdg@rdg.com", createdPerson.getEmail());
  }

  @Test
  @Order(2)
  @DisplayName("JUnit integration given person object when get one person should return a person object")
  void IntegrationTestGivenPersonObject_when_UpdateOnePerson_ShouldReturnAUpdatedPersonObject()
      throws JsonMappingException, JsonProcessingException {
    person.setFirstName("Rafael");
    person.setEmail("rafael@rafa.com");
    String content = given()
        .spec(specification)
        .contentType(TestConfig.CONTENT_TYPE_JSON)
        .body(person)
        .when()
        .put()
        .then()
        .statusCode(200)
        .extract()
        .body()
        .asString();

    Person updatedPerson = mapper.readValue(content, Person.class);
    person = updatedPerson;

    assertNotNull(updatedPerson);
    assertNotNull(updatedPerson.getId());
    assertNotNull(updatedPerson.getFirstName());
    assertNotNull(updatedPerson.getLastName());
    assertNotNull(updatedPerson.getAddress());
    assertNotNull(updatedPerson.getGender());
    assertNotNull(updatedPerson.getEmail());

    assertTrue(updatedPerson.getId() > 0);
    assertEquals("Rafael", updatedPerson.getFirstName());
    assertEquals("Silva", updatedPerson.getLastName());
    assertEquals("Salvador - BA", updatedPerson.getAddress());
    assertEquals("Male", updatedPerson.getGender());
    assertEquals("rafael@rafa.com", updatedPerson.getEmail());
  }

  @Test
  @Order(3)
  @DisplayName("JUnit integration given person object when get one person should return a person object")
  void IntegrationTestGivenPersonObject_when_FindById_ShouldReturnAPersonObject()
      throws JsonMappingException, JsonProcessingException {
    String content = given()
        .spec(specification)
        .pathParam("id", person.getId())
        .when()
        .get("/{id}")
        .then()
        .statusCode(200)
        .extract()
        .body()
        .asString();

    Person foundPerson = mapper.readValue(content, Person.class);

    assertNotNull(foundPerson);
    assertNotNull(foundPerson.getId());
    assertNotNull(foundPerson.getFirstName());
    assertNotNull(foundPerson.getLastName());
    assertNotNull(foundPerson.getAddress());
    assertNotNull(foundPerson.getGender());
    assertNotNull(foundPerson.getEmail());

    assertTrue(foundPerson.getId() > 0);
    assertEquals("Rafael", foundPerson.getFirstName());
    assertEquals("Silva", foundPerson.getLastName());
    assertEquals("Salvador - BA", foundPerson.getAddress());
    assertEquals("Male", foundPerson.getGender());
    assertEquals("rafael@rafa.com", foundPerson.getEmail());
  }

  @Test
  @Order(4)
  @DisplayName("JUnit integration when get all persons should return a person list")
  void IntegrationTest_when_FindAll_ShouldReturnAPersonList()
      throws JsonMappingException, JsonProcessingException {

    Person anotherPerson = new Person("Gabriel", "Santana", "SP-SP", "Male", "gb@gb.com");

    given()
        .spec(specification)
        .contentType(TestConfig.CONTENT_TYPE_JSON)
        .body(anotherPerson)
        .when()
        .post()
        .then()
        .statusCode(200);

    String content = given()
        .spec(specification)
        .when()
        .get()
        .then()
        .statusCode(200)
        .extract()
        .body()
        .asString();

    Person[] myArray = mapper.readValue(content, Person[].class);
    List<Person> people = Arrays.asList(myArray);
    Person foundPersonOne = people.getFirst();

    assertNotNull(foundPersonOne);
    assertNotNull(foundPersonOne.getId());
    assertNotNull(foundPersonOne.getFirstName());
    assertNotNull(foundPersonOne.getLastName());
    assertNotNull(foundPersonOne.getAddress());
    assertNotNull(foundPersonOne.getGender());
    assertNotNull(foundPersonOne.getEmail());

    assertTrue(foundPersonOne.getId() > 0);
    assertEquals("Rafael", foundPersonOne.getFirstName());
    assertEquals("Silva", foundPersonOne.getLastName());
    assertEquals("Salvador - BA", foundPersonOne.getAddress());
    assertEquals("Male", foundPersonOne.getGender());
    assertEquals("rafael@rafa.com", foundPersonOne.getEmail());

    Person foundPersonTwo = people.get(1);

    assertNotNull(foundPersonTwo);
    assertNotNull(foundPersonTwo.getId());
    assertNotNull(foundPersonTwo.getFirstName());
    assertNotNull(foundPersonTwo.getLastName());
    assertNotNull(foundPersonTwo.getAddress());
    assertNotNull(foundPersonTwo.getGender());
    assertNotNull(foundPersonTwo.getEmail());

    assertTrue(foundPersonTwo.getId() > 0);
    assertEquals("Gabriel", foundPersonTwo.getFirstName());
    assertEquals("Santana", foundPersonTwo.getLastName());
    assertEquals("SP-SP", foundPersonTwo.getAddress());
    assertEquals("Male", foundPersonTwo.getGender());
    assertEquals("gb@gb.com", foundPersonTwo.getEmail());
  }

  @Test
  @Order(5)
  @DisplayName("JUnit integration given person object when delete should return no content")
  void IntegrationTestGivenPersonObject_when_delete_ShouldReturnNoContent()
      throws JsonMappingException, JsonProcessingException {
    given()
        .spec(specification)
        .pathParam("id", person.getId())
        .when()
        .delete("/{id}")
        .then()
        .statusCode(204);
  }
}
