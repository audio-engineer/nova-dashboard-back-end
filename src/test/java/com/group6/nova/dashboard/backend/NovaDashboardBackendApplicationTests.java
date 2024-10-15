package com.group6.nova.dashboard.backend;

import org.springframework.boot.SpringApplication;

/// The main NovaDashboardBackendApplication tests.
///
/// [SuppressWarnings] explanation:
/// - `NonFinalUtilityClass`, `UtilityClassWithPublicConstructor`,
/// `UtilityClassWithoutPrivateConstructor`, `PMD.UseUtilityClass` - Main classes always declare a
/// static main method, but cannot be declared final utility classes.
/// - `PMD.TestClassWithoutTestCases` - Since this is a main class, it doesn't include any concrete
/// tests.
///
/// @author Martin Kedmenec
@SuppressWarnings({
  "NonFinalUtilityClass",
  "UtilityClassWithPublicConstructor",
  "UtilityClassWithoutPrivateConstructor",
  "PMD.TestClassWithoutTestCases",
  "PMD.UseUtilityClass"
})
public class NovaDashboardBackendApplicationTests {
  /// Constructor.
  public NovaDashboardBackendApplicationTests() {
    //
  }

  /// The main test application entrypoint.
  ///
  /// @param args the application arguments
  public static void main(final String[] args) {
    SpringApplication.from(NovaDashboardBackendApplication::main)
        .with(TestcontainersConfiguration.class)
        .run(args);
  }
}
