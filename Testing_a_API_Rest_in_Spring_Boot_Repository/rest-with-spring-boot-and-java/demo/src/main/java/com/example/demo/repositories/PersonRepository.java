package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

  Optional<Person> findByEmail(String email);

  // Define custom query using JPQL with index paremeters
  @Query("SELECT p FROM Person p WHERE p.firstName = ?1 AND p.lastName = ?2")
  Person findByJPQL(String firstName, String lastName);

  // Define custom query using JPQL with named parameters
  @Query("SELECT p FROM Person p WHERE p.firstName =:firstName AND p.lastName =:lastName")
  Person findByJPQLNamedParameters(
      @Param("firstName") String firstName, @Param("lastName") String lastName);

  // Define custom query using native SQL with index parameters
  @Query(value = "SELECT * FROM Person p WHERE p.first_name = ?1 AND p.last_name = ?2", nativeQuery = true)
  Person findByNativeSQL(String firstName, String lastName);

  // Define custom query using native SQL with named parameters
  @Query(value = "SELECT * FROM Person p WHERE p.first_name =:firstName AND p.last_name =:lastName", nativeQuery = true)
  Person findByNativeSQLwithnamedParameters(
      @Param("firstName") String firstName, @Param("lastName") String lastName);
}
