package com.group6.novadashboardbackend;

import static com.group6.novadashboardbackend.WarningValue.DESIGN_FOR_EXTENSION;

import java.util.List;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

/// Configuration for the Testcontainer setup.
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

  /// Constructor.
  public TestcontainersConfiguration() {
    //
  }

  /// `@SuppressWarnings` explanation:
  ///
  /// - A method annotated with [org.springframework.context.annotation.Bean] in a
  /// [org.springframework.context.annotation.Configuration] annotated class cannot be final.
  /// @return a container instance
  @Bean
  @ServiceConnection
  @RestartScope
  @SuppressWarnings(DESIGN_FOR_EXTENSION)
  public PostgreSQLContainer<?> postgreSqlContainer() {
    try (@SuppressWarnings("LocalCanBeFinal")
        PostgreSQLContainer<?> postgreSqlContainer =
            new PostgreSQLContainer<>("postgres:17-alpine")) {
      postgreSqlContainer
          .withExposedPorts()
          .withDatabaseName("nova")
          .withUsername(username)
          .withPassword(password)
          .withReuse(true);

      final List<String> portBindings = List.of("5432:5432");
      postgreSqlContainer.setPortBindings(portBindings);

      postgreSqlContainer.start();

      return postgreSqlContainer;
    }
  }
}
