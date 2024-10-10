package com.group6.novadashboardbackend.model;

import static com.group6.novadashboardbackend.model.ValidationMessage.INVALID_EMAIL_FORMAT;
import static com.group6.novadashboardbackend.model.ValidationMessage.REQUIRED_EMAIL;
import static com.group6.novadashboardbackend.model.ValidationMessage.REQUIRED_PASSWORD;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/// LoginRequest is a record that represents a login request containing user credentials.
/// @param email the email of the user attempting to log in
/// @param password the password of the user attempting to log in
public record LoginRequest(
    @Email(message = INVALID_EMAIL_FORMAT) @NotBlank(message = REQUIRED_EMAIL) String email,
    @NotBlank(message = REQUIRED_PASSWORD) String password) {}
