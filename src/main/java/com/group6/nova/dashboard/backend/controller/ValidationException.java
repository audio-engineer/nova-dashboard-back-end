package com.group6.nova.dashboard.backend.controller;

import java.io.Serial;

/// Checked validation exception to be thrown in a controller when the validator returns a non-empty
/// error object.
///
/// @author Martin Kedmenec
/// @see Exception
public class ValidationException extends Exception {
  @Serial private static final long serialVersionUID = 951956304624340199L;

  /// The default beginning of the exception message
  private static final String FILE_VALIDATION_FAILED = "File validation failed: ";

  /* default */ ValidationException(final String message) {
    super(FILE_VALIDATION_FAILED + message);
  }

  /* default */ ValidationException(final String message, final Throwable cause) {
    super(FILE_VALIDATION_FAILED + message, cause);
  }
}
