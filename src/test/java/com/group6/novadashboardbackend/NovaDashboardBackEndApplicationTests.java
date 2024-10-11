package com.group6.novadashboardbackend;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/// The main NovaDashboardBackEndApplication tests.
@SuppressWarnings("ClassNamePrefixedWithPackageName")
@Slf4j
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
final class NovaDashboardBackEndApplicationTests {
  /// Mock PostgreSQL database
  @Container
  @ServiceConnection
  private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER =
      new PostgreSQLContainer<>("postgres:17-alpine");

  private NovaDashboardBackEndApplicationTests() {
    //
  }

  @BeforeAll
  static void beforeAll() {
    POSTGRE_SQL_CONTAINER.start();

    if (!POSTGRE_SQL_CONTAINER.isRunning()) {
      throw new IllegalStateException("PostgreSQL container did not start properly");
    }

    final String jdbcUrl = POSTGRE_SQL_CONTAINER.getJdbcUrl();

    log.info("PostgreSQL Container JDBC URL: {}", jdbcUrl);
  }

  @AfterAll
  static void afterAll() {
    POSTGRE_SQL_CONTAINER.stop();
  }

  @Test
  void contextLoads() {
    final int number = 1;
    final int expected = 1;

    assertThat(number).isEqualTo(expected);
  }
}
