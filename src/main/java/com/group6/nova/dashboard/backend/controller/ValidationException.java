package com.group6.nova.dashboard.backend.controller;

import java.io.Serial;

/// Unchecked validation exception to be thrown in a controller when the validator returns a
/// non-empty error object.
///
/// @author Martin Kedmenec
/// @see RuntimeException
class ValidationException extends RuntimeException {
  @Serial private static final long serialVersionUID = 951956304624340199L;

  /* default */ ValidationException(final String message) {
    super("File validation failed: " + message);
  }
}
