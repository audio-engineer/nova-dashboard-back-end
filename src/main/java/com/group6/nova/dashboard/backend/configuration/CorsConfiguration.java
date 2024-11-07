package com.group6.nova.dashboard.backend.configuration;

import com.group6.nova.dashboard.backend.WarningValue;
import java.util.List;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
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
/// @author Martin Kedmenec
/// @see CorsConfigurationSource
/// @see UrlBasedCorsConfigurationSource
@Configuration
@NoArgsConstructor
class CorsConfiguration {
  /// [SuppressWarnings] explanation:
  /// - `DesignForExtension` - A method annotated with [Bean] in a [Configuration] annotated class
  /// cannot be final.
  @Bean
  @SuppressWarnings(WarningValue.DESIGN_FOR_EXTENSION)
  /* default */ CorsConfigurationSource corsConfigurationSource(
      @Value("${security.cors.allowed-origins}") final List<String> allowedOrigins) {
    final org.springframework.web.cors.CorsConfiguration configuration =
        new org.springframework.web.cors.CorsConfiguration();

    configuration.setAllowedOrigins(allowedOrigins);

    final String get = HttpMethod.GET.name();
    final String put = HttpMethod.PUT.name();
    final String post = HttpMethod.POST.name();
    final List<String> allowedMethods = List.of(get, put, post);
    configuration.setAllowedMethods(allowedMethods);

    final List<String> allowedHeaders =
        List.of(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE);
    configuration.setAllowedHeaders(allowedHeaders);

    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}
