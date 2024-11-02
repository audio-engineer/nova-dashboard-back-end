package com.group6.nova.dashboard.backend.service;

import java.io.Serial;

/// Exception indicating an error occurred while attempting to launch a batch import job.
///
/// @author Martin Kedmenec
/// @see RuntimeException
class ImportJobException extends RuntimeException {
  @Serial private static final long serialVersionUID = 7687091833844853416L;

  /* default */ ImportJobException(final Throwable cause) {
    super("Failed to launch import job.", cause);
  }
}
