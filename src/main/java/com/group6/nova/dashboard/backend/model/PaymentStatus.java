package com.group6.nova.dashboard.backend.model;

import lombok.AllArgsConstructor;
import lombok.ToString;

/// Enum representing the various payment statuses an order can have.
///
/// @author Martin Kedmenec
@ToString
@AllArgsConstructor
public enum PaymentStatus {
  /// Order is paid
  PAID("Paid"),
  /// Order is unpaid
  UNPAID("Unpaid");

  /// Holds the payment status value
  private final String status;

  /// Parses a string and returns the corresponding [PaymentStatus] enum constant.
  /// @param status the payment status string
  /// @return the corresponding [PaymentStatus] enum constant
  /// @throws IllegalArgumentException if the string does not match any constant
  public static PaymentStatus fromString(final String status) {
    for (final PaymentStatus paymentStatus : values()) {
      if (paymentStatus.status.equalsIgnoreCase(status)) {
        return paymentStatus;
      }
    }

    throw new IllegalArgumentException("Unknown payment status: " + status);
  }
}
