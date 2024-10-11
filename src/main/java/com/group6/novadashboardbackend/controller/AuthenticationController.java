package com.group6.novadashboardbackend.controller;

import com.group6.novadashboardbackend.model.LoginRequest;
import com.group6.novadashboardbackend.model.LoginResponse;
import com.group6.novadashboardbackend.model.SignupRequest;
import com.group6.novadashboardbackend.service.AuthenticationServiceInterface;
import com.group6.novadashboardbackend.service.JwtServiceInterface;
import jakarta.validation.Valid;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/// This controller handles authentication-related operations such as user sign-up and login.
@ToString
@RestController
@RequestMapping("authentication")
public class AuthenticationController {
  /// [AuthenticationServiceInterface] instance
  private final AuthenticationServiceInterface authenticationService;

  /// [JwtServiceInterface] instance
  private final JwtServiceInterface jwtServiceInterface;

  /// Constructor.
  /// @param authenticationServiceInterfaceParameter AuthenticationServiceInterface instance
  /// @param jwtServiceInterfaceParameter JwtServiceInterface instance
  public AuthenticationController(
      final AuthenticationServiceInterface authenticationServiceInterfaceParameter,
      final JwtServiceInterface jwtServiceInterfaceParameter) {
    authenticationService = authenticationServiceInterfaceParameter;
    jwtServiceInterface = jwtServiceInterfaceParameter;
  }

  /// Signs up a new user.
  /// @param signupRequest the signup request containing user details such as email, first name,
  /// last name, and password
  /// @return ResponseEntity containing the registered user information
  @PostMapping("/signup")
  public final ResponseEntity<UserDetails> signUp(
      @Valid @RequestBody final SignupRequest signupRequest) {
    final UserDetails registeredUser = authenticationService.signUp(signupRequest);

    return ResponseEntity.ok(registeredUser);
  }

  /// Authenticates a user and provides a JWT token upon successful authentication.
  /// @param loginRequest the login request containing user credentials
  /// @return ResponseEntity containing the login response with a JWT token and expiration time
  @PostMapping("/login")
  public final ResponseEntity<LoginResponse> logIn(
      @Valid @RequestBody final LoginRequest loginRequest) {
    final UserDetails authenticatedUser = authenticationService.authenticate(loginRequest);

    final String jwtToken = jwtServiceInterface.generateToken(authenticatedUser);
    final long expirationTime = jwtServiceInterface.getExpirationTime();

    final LoginResponse loginResponse = new LoginResponse(jwtToken, expirationTime);

    return ResponseEntity.ok(loginResponse);
  }
}
