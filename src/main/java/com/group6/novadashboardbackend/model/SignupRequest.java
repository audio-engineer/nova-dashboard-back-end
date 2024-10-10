package com.group6.novadashboardbackend.model;

import static com.group6.novadashboardbackend.model.ValidationMessage.INVALID_EMAIL_FORMAT;
import static com.group6.novadashboardbackend.model.ValidationMessage.REQUIRED_EMAIL;
import static com.group6.novadashboardbackend.model.ValidationMessage.REQUIRED_PASSWORD;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/// A record representing a signup request containing user details.
/// @param email the email of the user signing up
/// @param firstName the first name of the user signing up
/// @param lastName the last name of the user signing up
/// @param password the password for the new account
public record SignupRequest(
    @Email(message = INVALID_EMAIL_FORMAT) @NotBlank(message = REQUIRED_EMAIL) String email,
    @NotBlank(message = "First name is required") String firstName,
    @NotBlank(message = "Last name is required") String lastName,
    @NotBlank(message = REQUIRED_PASSWORD)
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password) {}
