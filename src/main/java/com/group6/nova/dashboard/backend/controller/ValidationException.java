package com.group6.nova.dashboard.backend.controller;

import java.io.Serial;

/// Checked validation exception to be thrown in a controller when the validator returns a non-empty
/// error object.
///
/// @author Martin Kedmenec
/// @see Exception
public class ValidationException extends Exception {
  @Serial private static final long serialVersionUID = 951956304624340199L;

  /* default */ ValidationException(final String message) {
    super("File validation failed: " + message);
  }
}
