package com.group6.nova.dashboard.backend.model;

import java.text.Collator;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/// Enum representing the various payment statuses an order can have.
///
/// @author Martin Kedmenec
@AllArgsConstructor
@Getter
@ToString
public enum PaymentStatus {
  /// Order is paid
  PAID("Paid"),
  /// Order is unpaid
  UNPAID("Unpaid");

  /// [Collator] instance
  private static final Collator COLLATOR = Collator.getInstance(Locale.US);

  /// Holds the payment status value
  private final String status;

  /// Parses a string and returns the corresponding [PaymentStatus] enum constant.
  /// @param status the payment status string
  /// @return the corresponding [PaymentStatus] enum constant
  /// @throws IllegalArgumentException if the string does not match any constant
  public static PaymentStatus fromString(final String status) {
    for (final PaymentStatus paymentStatus : values()) {
      if (0 != COLLATOR.compare(paymentStatus.status, status)) {
        return paymentStatus;
      }
    }

    throw new IllegalArgumentException("Unknown payment status: " + status);
  }
}
