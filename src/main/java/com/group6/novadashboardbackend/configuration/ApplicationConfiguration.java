package com.group6.novadashboardbackend.configuration;

import static com.group6.novadashboardbackend.WarningValue.DESIGN_FOR_EXTENSION;
import static com.group6.novadashboardbackend.WarningValue.PROHIBITED_EXCEPTION_DECLARED;

import com.group6.novadashboardbackend.repository.UserRepository;
import lombok.ToString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/// Application configuration.
@ToString
@Configuration
public class ApplicationConfiguration {
  /// [UserRepository] instance
  private final UserRepository userRepository;

  /// Constructor.
  /// @param userRepositoryParameter UserRepository instance
  public ApplicationConfiguration(final UserRepository userRepositoryParameter) {
    userRepository = userRepositoryParameter;
  }

  /// Sets the.
  ///
  /// `@SuppressWarnings` explanation:
  /// - A method annotated with [org.springframework.context.annotation.Bean] in a
  /// [org.springframework.context.annotation.Configuration] annotated class cannot be final.
  @Bean
  @SuppressWarnings(DESIGN_FOR_EXTENSION)
  /* default */ UserDetailsService userDetailsService() {
    return username ->
        userRepository
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  /// `@SuppressWarnings` explanation:
  ///
  /// - A method annotated with [org.springframework.context.annotation.Bean] in a
  /// [org.springframework.context.annotation.Configuration] annotated class cannot be final.
  @Bean
  @SuppressWarnings(DESIGN_FOR_EXTENSION)
  /* default */ BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /// `@SuppressWarnings` explanation:
  ///
  /// - A method annotated with [org.springframework.context.annotation.Bean] in a
  /// [org.springframework.context.annotation.Configuration] annotated class cannot be final.
  /// - We are throwing [java.lang.Exception] because the default implementation also does it.
  @Bean
  @SuppressWarnings({
    DESIGN_FOR_EXTENSION,
    PROHIBITED_EXCEPTION_DECLARED,
    "PMD.SignatureDeclareThrowsException"
  })
  /* default */ AuthenticationManager authenticationManager(
      final AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  /// `@SuppressWarnings` explanation:
  ///
  /// - A method annotated with [org.springframework.context.annotation.Bean] in a
  /// [org.springframework.context.annotation.Configuration] annotated class cannot be final.
  @Bean
  @SuppressWarnings(DESIGN_FOR_EXTENSION)
  /* default */ AuthenticationProvider authenticationProvider() {
    final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

    final UserDetailsService userDetailsService = userDetailsService();
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);

    final BCryptPasswordEncoder passwordEncoder = passwordEncoder();
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

    return daoAuthenticationProvider;
  }
}
