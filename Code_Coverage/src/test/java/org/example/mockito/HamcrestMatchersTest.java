package org.example.mockito;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestMatchersTest {

  @Test
  void test() {
    // Given / Arrange
    List<Integer> scores = Arrays.asList(99, 100, 101, 105);

    // When / Act
    assertThat(scores, hasSize(4));
    assertThat(scores, hasItems(99, 101));
    assertThat(scores, everyItem(greaterThan(98)));
    assertThat(scores, everyItem(lessThan(106)));

    // Check Strings
    assertThat("", is(emptyString()));
    assertThat(null, is(emptyOrNullString()));

    // Arrays
    Integer[] myArray = {1, 2, 3};
    assertThat(myArray, arrayWithSize(3));
    assertThat(myArray, arrayContaining(1, 2, 3));
    assertThat(myArray, arrayContainingInAnyOrder(1, 3, 2));

    // Then / Assert
  }
}
