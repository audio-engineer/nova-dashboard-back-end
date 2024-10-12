package com.group6.novadashboardbackend;

import org.springframework.boot.SpringApplication;

/// The main NovaDashboardBackEndApplication tests.
@SuppressWarnings({
  "NonFinalUtilityClass",
  "UtilityClassWithPublicConstructor",
  "UtilityClassWithoutPrivateConstructor",
  "ClassNamePrefixedWithPackageName",
  "PMD.TestClassWithoutTestCases",
  "PMD.UseUtilityClass"
})
public class NovaDashboardBackEndApplicationTests {
  /// Constructor.
  public NovaDashboardBackEndApplicationTests() {
    //
  }

  /// The main test application entrypoint.
  /// @param args the application arguments
  public static void main(final String[] args) {
    SpringApplication.from(NovaDashboardBackEndApplication::main)
        .with(TestcontainersConfiguration.class)
        .run(args);
  }
}
