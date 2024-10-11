package com.group6.novadashboardbackend.service;

import com.group6.novadashboardbackend.model.LoginRequest;
import com.group6.novadashboardbackend.model.SignupRequest;
import com.group6.novadashboardbackend.model.User;
import com.group6.novadashboardbackend.repository.UserRepository;
import lombok.ToString;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/// Authentication service.
@ToString
@Service
public class AuthenticationService implements AuthenticationServiceInterface {
  /// [UserRepository] instance
  private final UserRepository userRepository;

  /// [AuthenticationManager] instance
  private final AuthenticationManager authenticationManager;

  /// [PasswordEncoder] instance
  private final PasswordEncoder passwordEncoder;

  /// Constructor.
  /// @param userRepositoryParameter UserRepository instance
  /// @param authenticationManagerParameter PasswordEncoder instance
  /// @param passwordEncoderParameter AuthenticationManager instance
  public AuthenticationService(
      final UserRepository userRepositoryParameter,
      final AuthenticationManager authenticationManagerParameter,
      final PasswordEncoder passwordEncoderParameter) {
    userRepository = userRepositoryParameter;
    authenticationManager = authenticationManagerParameter;
    passwordEncoder = passwordEncoderParameter;
  }

  /// {@inheritDoc}
  /// This is the default method implementation.
  @Override
  public final UserDetails signUp(final SignupRequest signupRequest) {
    final String firstName = signupRequest.firstName();
    final String lastName = signupRequest.lastName();
    final String email = signupRequest.email();
    final String password = signupRequest.password();
    final String encodedPassword = passwordEncoder.encode(password);

    @SuppressWarnings("UseOfConcreteClass") // We can only persist objects of concrete classes
    final User user = new User(firstName, lastName, email, encodedPassword);

    return userRepository.save(user);
  }

  /// {@inheritDoc}
  /// This is the default method implementation.
  @Override
  public final UserDetails authenticate(final LoginRequest loginRequest) {
    final String loginRequestEmail = loginRequest.email();
    final String loginRequestPassword = loginRequest.password();

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequestEmail, loginRequestPassword));

    return userRepository.findByEmail(loginRequestEmail).orElseThrow();
  }
}
