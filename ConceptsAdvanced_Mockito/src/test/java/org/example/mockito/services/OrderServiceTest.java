package org.example.mockito.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

public class OrderServiceTest {

  private final OrderService service = new OrderService();
  private final UUID defaultUuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
  private final LocalDateTime defaultLocalDateTime = LocalDateTime.of(2024, 6, 13, 11, 20);

  @DisplayName("Should Include Random OrderId When No OrderId Exists")
  @Test
  void testShouldIncludeRandomOrderId_When_NoOrderIdExists() {
    // Given / Arrange
    try (MockedStatic<UUID> mockedUuid = mockStatic(UUID.class)) {
      mockedUuid.when(UUID::randomUUID).thenReturn(defaultUuid);
      // When / Act
      Order result = service.createOrder("MacBook Pro", 0L, null);
      // Then / Assert",
      assertEquals(defaultUuid.toString(), result.getId());
    }
  }

  @DisplayName("Should Include Current Time When Create a New Order")
  @Test
  void testShouldIncludeCurrentTime_When_CreateANewOrder() {
    // Given / Arrange
    try (MockedStatic<LocalDateTime> mockedUuid = mockStatic(LocalDateTime.class)) {
      mockedUuid.when(LocalDateTime::now).thenReturn(defaultLocalDateTime);
      // When / Act
      Order result = service.createOrder("MacBook Pro", 0L, null);
      // Then / Assert",
      assertEquals(defaultLocalDateTime, result.getCreationDate());
    }
  }
}
