package com.group6.nova.dashboard.backend.model;

import java.io.Serial;

/// This exception is used specifically by the [OrderStatus#fromString] and
/// [PaymentStatus#fromString] methods to signal that the provided status value does not match any
/// defined constants.
///
/// @author Martin Kedmenec
/// @see RuntimeException
class UnknownStatusException extends RuntimeException {
  @Serial private static final long serialVersionUID = -2728165814519235994L;

  /* default */ UnknownStatusException(final String message) {
    super(message);
  }
}
