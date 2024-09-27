package com.group6.novadashboardbackend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/** The main NovaDashboardBackEndApplication tests. */
@SpringBootTest
class NovaDashboardBackEndApplicationTests {
  /** The NovaDashboardBackEndApplicationTests constructor. */
  public NovaDashboardBackEndApplicationTests() {
    //
  }

  @Test
  void contextLoads() {
    final int one = 1;
    final int expected = 1;

    assertThat(one).isEqualTo(expected);
  }
}
