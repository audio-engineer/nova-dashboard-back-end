package com.group6.nova.dashboard.backend;

import java.util.List;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

/// Configuration for the Testcontainers setup.
///
/// Usage: Annotate a test class with `@Import(TestcontainersConfiguration.class)`.
///
/// [SuppressWarnings] explanation:
/// - `PMD.TestClassWithoutTestCases` - This is a configuration class and thus doesn't include any
/// concrete tests.
///
/// @author Martin Kedmenec
@NoArgsConstructor
@ToString
@TestConfiguration(proxyBeanMethods = false)
@SuppressWarnings("PMD.TestClassWithoutTestCases")
public class TestcontainersConfiguration {
  /// Database username
  @Value("${spring.datasource.username}")
  private String username;

  /// Database password
  @Value("${spring.datasource.password}")
  private String password;

  /// [SuppressWarnings] explanation:
  /// - `DesignForExtension` - A method annotated with [Bean] in a [TestConfiguration] annotated
  /// class cannot be final.
  /// - `LocalCanBeFinal` - A try-with-resources statement cannot declare final variables.
  ///
  /// @return a mock PostgreSQL container
  @Bean
  @ServiceConnection
  @RestartScope
  @SuppressWarnings(WarningValue.DESIGN_FOR_EXTENSION)
  /* default */ PostgreSQLContainer<?> postgreSqlContainer() {
    try (@SuppressWarnings("LocalCanBeFinal")
        PostgreSQLContainer<?> postgreSqlContainer =
            new PostgreSQLContainer<>("postgres:17-alpine")) {
      postgreSqlContainer
          .withExposedPorts()
          .withDatabaseName("nova")
          .withUsername(username)
          .withPassword(password);

      final List<String> portBindings = List.of("5432:5432");
      postgreSqlContainer.setPortBindings(portBindings);

      postgreSqlContainer.start();

      return postgreSqlContainer;
    }
  }
}
