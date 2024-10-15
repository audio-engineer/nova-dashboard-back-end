package com.group6.nova.dashboard.backend.configuration;

import static com.group6.nova.dashboard.backend.WarningValue.DESIGN_FOR_EXTENSION;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import java.util.List;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/// Configuration class for setting up Cross-Origin Resource Sharing (CORS) policies.
///
/// Configures CORS settings for the application using [CorsConfigurationSource].
///
/// Key aspects of the configuration:
/// - Permits requests from allowed origins defined in the application properties.
/// - Supports GET and POST methods for CORS requests.
/// - Allows headers like `Authorization` and `Content-Type` in CORS requests.
///
/// Annotations:
/// - [NoArgsConstructor] - Generates a no-argument constructor.
/// - [Configuration] - Marks the class as a source of bean definitions.
///
/// @author Martin Kedmenec
/// @see CorsConfigurationSource
/// @see UrlBasedCorsConfigurationSource
@NoArgsConstructor
@Configuration
class CorsConfiguration {
  /// [SuppressWarnings] explanation:
  /// - `DesignForExtension` - A method annotated with [org.springframework.context.annotation.Bean]
  ///  in a [org.springframework.context.annotation.Configuration] annotated class cannot be final.
  @Bean
  @SuppressWarnings(DESIGN_FOR_EXTENSION)
  /* default */ CorsConfigurationSource corsConfigurationSource(
      @Value("${security.cors.allowed-origins}") final List<String> allowedOrigins) {
    final org.springframework.web.cors.CorsConfiguration configuration =
        new org.springframework.web.cors.CorsConfiguration();

    configuration.setAllowedOrigins(allowedOrigins);

    final String get = HttpMethod.GET.name();
    final String post = HttpMethod.POST.name();
    final List<String> allowedMethods = List.of(get, post);
    configuration.setAllowedMethods(allowedMethods);

    final List<String> allowedHeaders = List.of(AUTHORIZATION, CONTENT_TYPE);
    configuration.setAllowedHeaders(allowedHeaders);

    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}
