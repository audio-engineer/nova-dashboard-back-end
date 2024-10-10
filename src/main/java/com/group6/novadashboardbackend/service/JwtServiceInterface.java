package com.group6.novadashboardbackend.service;

import org.springframework.security.core.userdetails.UserDetails;

/// Interface representing the JWT service for managing JSON Web Tokens.
public interface JwtServiceInterface {
  /// The value of the expiration time as configured in application.yml.
  /// @return the configured expiration time
  long getExpirationTime();

  /// Generates a JWT token for the given user.
  /// @param user the user for whom the token is to be generated
  /// @return the generated JWT token
  String generateToken(UserDetails user);

  /// Validates the given JWT token against the provided user details.
  /// @param token the JWT token to be validated
  /// @param userDetails the user details to be used for validation
  /// @return true if the token is valid, false otherwise
  boolean isTokenValid(CharSequence token, UserDetails userDetails);

  /// Extracts the username from the given JWT token.
  /// @param token the JWT token from which the username is to be extracted
  /// @return the extracted username
  String extractUsername(CharSequence token);
}
