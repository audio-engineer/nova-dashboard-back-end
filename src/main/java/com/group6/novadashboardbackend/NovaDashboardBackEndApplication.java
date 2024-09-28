package com.group6.novadashboardbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** The main application entrypoint object. */
@SuppressWarnings("PMD.UseUtilityClass")
@SpringBootApplication
public class NovaDashboardBackEndApplication {
  /** The default NovaDashboardBackEndApplication constructor. */
  public NovaDashboardBackEndApplication() {
    //
  }

  /**
   * The main application entrypoint.
   *
   * @param args The application arguments.
   */
  public static void main(final String[] args) {
    SpringApplication.run(NovaDashboardBackEndApplication.class, args);
  }
}
