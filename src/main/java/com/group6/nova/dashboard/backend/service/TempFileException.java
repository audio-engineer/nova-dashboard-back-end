package com.group6.nova.dashboard.backend.service;

import java.io.Serial;

/// Exception indicating an error occurred while dealing with the CSV import temp file.
///
/// @author Martin Kedmenec
/// @see RuntimeException
class TempFileException extends RuntimeException {
  @Serial private static final long serialVersionUID = -7210511908710250053L;

  /* default */ TempFileException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
