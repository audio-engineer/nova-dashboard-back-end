package com.group6.novadashboardbackend.configuration;

import static com.group6.novadashboardbackend.WarningValue.DESIGN_FOR_EXTENSION;
import static com.group6.novadashboardbackend.WarningValue.PROHIBITED_EXCEPTION_DECLARED;
import static com.group6.novadashboardbackend.configuration.AllowedHeader.AUTHORIZATION;

import java.util.List;
import java.util.Objects;
import lombok.ToString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/// The SecurityConfiguration class configures the security settings for the application.
/// It consists of beans and methods to set up HTTP security, JWT authentication, CSRF
/// configuration, CORS policies, and session management.
@ToString
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
  /// [AuthenticationProvider] instance
  private final AuthenticationProvider authenticationProvider;

  /// [JwtAuthenticationFilter] instance
  @SuppressWarnings("UseOfConcreteClass")
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  /// Constructor.
  /// @param jwtAuthenticationFilterParameter JwtAuthenticationFilter instance
  /// @param authenticationProviderParameter AuthenticationProvider instance
  @SuppressWarnings("UseOfConcreteClass")
  public SecurityConfiguration(
      final JwtAuthenticationFilter jwtAuthenticationFilterParameter,
      final AuthenticationProvider authenticationProviderParameter) {
    // Done to mitigate SpotBugs bug EI_EXPOSE_REP2
    jwtAuthenticationFilter =
        Objects.requireNonNull(
            jwtAuthenticationFilterParameter, "JwtAuthenticationFilter must not be null.");
    authenticationProvider = authenticationProviderParameter;
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
  /* default */ SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            (authorize) ->
                authorize
                    .requestMatchers("/authentication/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .sessionManagement(
            (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  /// `@SuppressWarnings` explanation:
  ///
  /// - A method annotated with [org.springframework.context.annotation.Bean] in a
  /// [org.springframework.context.annotation.Configuration] annotated class cannot be final.
  /// - We are throwing [java.lang.Exception] because the default implementation also does it.
  @Bean
  @SuppressWarnings(DESIGN_FOR_EXTENSION)
  /* default */ CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration configuration = new CorsConfiguration();

    final List<String> allowedOrigins = List.of("http://localhost:3000");
    configuration.setAllowedOrigins(allowedOrigins);

    final List<String> allowedMethods = List.of("GET", "POST");
    configuration.setAllowedMethods(allowedMethods);

    final List<String> allowedHeaders = List.of(AUTHORIZATION, "Content-Type");
    configuration.setAllowedHeaders(allowedHeaders);

    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}
