package com.group6.novadashboardbackend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

/** The main NovaDashboardBackEndApplication tests. */
@SuppressWarnings("ClassNamePrefixedWithPackageName")
@Testcontainers
@SpringBootTest
final class NovaDashboardBackEndApplicationTests {
  /** Mock PostgreSQL database. */
  private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER =
      new PostgreSQLContainer<>("postgres:17-alpine");

  /** The default NovaDashboardBackEndApplicationTests constructor. */
  private NovaDashboardBackEndApplicationTests() {
    //
  }

  @BeforeAll
  static void beforeAll() {
    POSTGRE_SQL_CONTAINER.start();
  }

  @AfterAll
  static void afterAll() {
    POSTGRE_SQL_CONTAINER.stop();
  }

  @SuppressWarnings("PMD.UnusedPrivateMethod")
  @DynamicPropertySource
  private static void configureProperties(final DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
    registry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);
  }

  @Test
  void contextLoads() {
    final int number = 1;
    final int expected = 1;

    assertThat(number).isEqualTo(expected);
  }
}
