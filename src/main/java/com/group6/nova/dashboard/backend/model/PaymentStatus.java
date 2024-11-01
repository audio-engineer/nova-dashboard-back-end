package com.group6.nova.dashboard.backend.model;

import java.text.Collator;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
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
  ///
  /// @param status the payment status string
  /// @return the corresponding [PaymentStatus] enum constant
  @NonNull
  public static PaymentStatus fromString(final String status) {
    for (final PaymentStatus paymentStatus : values()) {
      @SuppressWarnings("CallToSimpleGetterFromWithinClass")
      final String statusValue = paymentStatus.getStatus();

      if (0 == COLLATOR.compare(statusValue, status)) {
        return paymentStatus;
      }
    }

    throw new UnknownStatusException("Unknown payment status: " + status);
  }
}
