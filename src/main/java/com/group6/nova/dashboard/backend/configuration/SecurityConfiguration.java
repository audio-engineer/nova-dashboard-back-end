package com.group6.nova.dashboard.backend.configuration;

import com.group6.nova.dashboard.backend.WarningValue;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/// Configuration class for setting up the security policies of the application.
///
/// Configures HTTP security settings for the application using [SecurityFilterChain].
///
/// Key aspects of the configuration:
/// - Disables Cross-Site Request Forgery (CSRF) protection, as it's unnecessary for stateless APIs.
/// - Permits unauthenticated access to the endpoints under the `/authentication/**` path.
/// - Enforces authentication for all other endpoints.
/// - Configures the application as a stateless service by setting [SessionCreationPolicy#STATELESS]
/// for session management, which means no session is created or used.
/// - Configures the OAuth2 resource server to validate JWT (JSON Web Tokens) using
/// [HttpSecurity#oauth2ResourceServer(Customizer)].
///
/// @author Martin Kedmenec
/// @see SecurityFilterChain
/// @see HttpSecurity
@Configuration
@EnableWebSecurity
@NoArgsConstructor
@ToString
class SecurityConfiguration {
  /// [SuppressWarnings] explanation:
  /// - `DesignForExtension` - A method annotated with [Bean] in a [Configuration] annotated class
  /// cannot be final.
  /// - `ProhibitedExceptionDeclared`, `PMD.SignatureDeclareThrowsException` - We are throwing
  /// [Exception] because the default implementation also does it.
  /// - `NestedMethodCall` - We are using the configuration DSL which uses nested method calls.
  @Bean
  @SuppressWarnings({
    WarningValue.DESIGN_FOR_EXTENSION,
    "ProhibitedExceptionDeclared",
    "PMD.SignatureDeclareThrowsException",
    "NestedMethodCall"
  })
  /* default */ SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        // TODO Remove this method call
        .authorizeHttpRequests(
            (authorize) ->
                authorize.requestMatchers("/**").permitAll().anyRequest().authenticated())
        .sessionManagement(
            (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));

    return http.build();
  }
}
