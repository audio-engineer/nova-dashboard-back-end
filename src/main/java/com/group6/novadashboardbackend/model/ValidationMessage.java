package com.group6.novadashboardbackend.model;

/// Contains package private validation error messages.
enum ValidationMessage {
  ;
  /// Message for email format
  /* default */ static final String INVALID_EMAIL_FORMAT = "Invalid email format";

  /// Message for required email
  /* default */ static final String REQUIRED_EMAIL = "Email is required";

  /// Message for required password
  /* default */ static final String REQUIRED_PASSWORD = "Password is required";
}
