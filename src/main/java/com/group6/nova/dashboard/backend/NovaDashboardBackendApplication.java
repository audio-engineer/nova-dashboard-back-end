package com.group6.nova.dashboard.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/// The main application entrypoint object.
///
/// [SuppressWarnings] explanation:
/// - `PMD.UseUtilityClass` - Main classes always declare a static main method, but cannot be
/// declared final utility classes.
///
/// @author Martin Kedmenec
@SuppressWarnings("PMD.UseUtilityClass")
@SpringBootApplication
public class NovaDashboardBackendApplication {
  /// Constructor.
  public NovaDashboardBackendApplication() {
    //
  }

  /// The main application entrypoint.
  /// @param args the application arguments
  public static void main(final String[] args) {
    SpringApplication.run(NovaDashboardBackendApplication.class, args);
  }
}
