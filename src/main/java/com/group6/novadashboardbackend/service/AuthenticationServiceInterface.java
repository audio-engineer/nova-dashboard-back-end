package com.group6.novadashboardbackend.service;

import com.group6.novadashboardbackend.model.LoginRequest;
import com.group6.novadashboardbackend.model.SignupRequest;
import java.util.NoSuchElementException;
import org.springframework.security.core.userdetails.UserDetails;

/// This interface defines the methods for user authentication and registration services.
public interface AuthenticationServiceInterface {
  /// Handles signup of new users.
  /// @param signupRequest the signup request body
  /// @return a User object
  UserDetails signUp(SignupRequest signupRequest);

  /// Authenticates a user by attempting to look the user up in the database.
  /// @param loginRequest the login request body
  /// @return a User object
  /// @throws NoSuchElementException if no matching user is found
  UserDetails authenticate(LoginRequest loginRequest);
}
