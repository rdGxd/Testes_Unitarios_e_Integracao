package org.example.business;

import org.example.service.CourseService;
import org.example.stubs.CourseServiceStub;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseBusinessStubTest {

  @Test
  void testCoursesRelatedToSpring_When_UsingAStub() {
    // Given / Arrange
    CourseService stubService = new CourseServiceStub();
    CourseBusiness business = new CourseBusiness(stubService);

    // When / Act
    var filteredCourses = business.retrieveCoursesRelatedToSpring("Rodrigo");

    // Then / Assert
    assertEquals(4, filteredCourses.size());
  }

  @Test
  void testCoursesRelatedToSpring_When_UsingAFooBarStudent() {
    // Given / Arrange
    CourseService stubService = new CourseServiceStub();
    CourseBusiness business = new CourseBusiness(stubService);

    // When / Act
    var filteredCourses = business.retrieveCoursesRelatedToSpring("Foo Bar");

    // Then / Assert
    assertEquals(0, filteredCourses.size());
  }
}
