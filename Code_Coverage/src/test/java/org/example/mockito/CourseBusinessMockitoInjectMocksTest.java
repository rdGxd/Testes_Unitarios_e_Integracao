package org.example.mockito;

import org.example.business.CourseBusiness;
import org.example.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CourseBusinessMockitoInjectMocksTest {

  @Mock
  CourseService mockService;

  @InjectMocks
  CourseBusiness business;
  // businnes = new CourseBusiness(mockService)

  @Captor
  ArgumentCaptor<String> argumentCaptor;
  
  List<String> courses;

  @BeforeEach
  void setup() {
    // Given / Arrange
    courses = Arrays.asList(
            "REST API's RESTFul do 0 à Azure com ASP.NET Core 5 e Docker",
            "Agile Desmistificado com Scrum, XP, Kanban e Trello",
            "Spotify Engineering Culture Desmistificado",
            "REST API's RESTFul do 0 à AWS com Spring Boot 3 Java e Docker",
            "Docker do Zero à Maestria - Contêinerização Desmistificada",
            "Docker para Amazon AWS Implante Apps Java e .NET com Travis CI",
            "Microsserviços do 0 com Spring Cloud, Spring Boot e Docker",
            "Arquitetura de Microsserviços do 0 com ASP.NET, .NET 6 e C#",
            "REST API's RESTFul do 0 à AWS com Spring Boot 3 Kotlin e Docker",
            "Kotlin para DEV's Java: Aprenda a Linguagem Padrão do Android",
            "Microsserviços do 0 com Spring Cloud, Kotlin e Docker"
    );
  }

  @Test
  void testCoursesRelatedToSpring_When_UsingAMock() {
    // Given / Arrange
    given(mockService.retrieveCourses("Rodrigo")).willReturn(courses);

    // When / Act
    var filteredCourses = business.retrieveCoursesRelatedToSpring("Rodrigo");

    // Then / Assert
    assertThat(filteredCourses.size(), is(4));
  }


  @DisplayName("Delete course not related to spring using mockito should call method")
  @Test
  void testDeleteCoursesNotRelatedToSpring_UsingMockitoVerify_Should_CallMethod_deleteCourse() {
    // Given / Arrange
    given(mockService.retrieveCourses("Rodrigo")).willReturn(courses);
    // When / Act
    business.deleteCoursesNotRelatedToSpring("Rodrigo");

    // Then / Assert
    verify(mockService, atLeastOnce()).deleteCourse("Spotify Engineering Culture Desmistificado"); // Pelo menos uma vez
    verify(mockService).deleteCourse("Spotify Engineering Culture Desmistificado");
    verify(mockService, never()).deleteCourse("REST API's RESTFul do 0 à AWS com Spring Boot 3 Java e Docker");
  }


  @DisplayName("Delete course not related to spring using mockito should call method V2")
  @Test
  void testDeleteCoursesNotRelatedToSpring_UsingMockitoVerify_Should_CallMethod_deleteCourseV2() {
    // Given / Arrange
    given(mockService.retrieveCourses("Rodrigo")).willReturn(courses);
    String course1 = "REST API's RESTFul do 0 à AWS com Spring Boot 3 Java e Docker";
    String course = "Spotify Engineering Culture Desmistificado";

    // When / Act
    business.deleteCoursesNotRelatedToSpring("Rodrigo");

    // Then / Assert
    then(mockService).should().deleteCourse(course); // Pelo menos uma vez
    then(mockService).should().deleteCourse(course);
    then(mockService).should(never()).deleteCourse(course1); // Nunca vou chamar
  }

  @DisplayName("Delete course not related to spring capture arguments should call method V3")
  @Test
  void testDeleteCoursesNotRelatedToSpring_CapturingArguments_Should_CallMethod_deleteCourseV2() {
    // Given / Arrange
    given(mockService.retrieveCourses("Rodrigo")).willReturn(courses);

    // ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

    // When / Act
    business.deleteCoursesNotRelatedToSpring("Rodrigo");

    // Then / Assert
    then(mockService).should(times(7)).deleteCourse(argumentCaptor.capture()); // Pelo menos uma vez
    assertThat(argumentCaptor.getAllValues().size(), is(7));
  }
}
