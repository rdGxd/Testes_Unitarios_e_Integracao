package org.example.mockito.staticwithparams;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

public class MyUtilsTest {

  @DisplayName("DisplayName")
  @Test
  void shouldMockStaticMethodWithParams() {
    try (MockedStatic<MyUtils> mockedStatic = mockStatic(MyUtils.class)) {
      // Given / Arrange
      mockedStatic.when(() -> MyUtils.getWelcomeMessage(eq("Rodrigo"), anyBoolean())).thenReturn("Howdy Rodrigo!");
    }
    // When / Act
    String result = MyUtils.getWelcomeMessage("Rodrigo", false);
    // Then / Assert
    assertEquals("Hello Rodrigo", result);
  }
}