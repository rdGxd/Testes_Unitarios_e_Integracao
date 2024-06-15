package com.example.demo.integrationstests.swagger;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.config.TestConfig;
import com.example.demo.integrationstests.testcontainers.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {

  @Test
  @DisplayName("JUnit test for Should Display Swagger UI Page")
  void testShouldDisplaySwaggerUiPage() {
    String content = given()
        .basePath("/swagger-ui/index.html")
        .port(TestConfig.SERVER_PORT)
        .when()
        .get()
        .then()
        .statusCode(200)
        .extract()
        .body()
        .asString();
    assertTrue(content.contains("Swagger UI"));
  }
}
