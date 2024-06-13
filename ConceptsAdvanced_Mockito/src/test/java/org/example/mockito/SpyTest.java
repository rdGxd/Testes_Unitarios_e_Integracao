package org.example.mockito;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

// O SPY É NORMALMENTE USADO PARA PROJETOS LEGADOS
public class SpyTest {


  @DisplayName("DisplayName")
  @Test
  void testV1() {
    // Given / Arrange",
    List<String> mockArrayList = spy(ArrayList.class);

    // When / Act & Then / Assert
    assertEquals(0, mockArrayList.size());

    when(mockArrayList.size()).thenReturn(5);
    mockArrayList.add("Foo-Bar");
    assertEquals(5, mockArrayList.size());
  }

  @Test
  void testV2() {
    // Given / Arrange",
    List<String> spyArrayList = spy(ArrayList.class);

    // When / Act & Then / Assert
    assertEquals(0, spyArrayList.size());

    spyArrayList.add("Foo-Bar");
    assertEquals(1, spyArrayList.size());

    spyArrayList.remove("Foo-Bar");
    assertEquals(0, spyArrayList.size());
  }

  @Test
  void testV3() {
    List<String> spyArrayList = spy(ArrayList.class);
    assertEquals(0, spyArrayList.size());
    when(spyArrayList.size()).thenReturn(5);
    assertEquals(5, spyArrayList.size());
  }

  @Test
  void testV4() {
    List<String> spyArrayList = spy(ArrayList.class);
    spyArrayList.add("Foo-Bar");

    verify(spyArrayList).add("Foo-Bar");
    //    verify(spyArrayList, never()).remove("Foo-Bar");
    verify(spyArrayList, never()).remove(anyString());
    verify(spyArrayList, never()).clear();
  }
}
