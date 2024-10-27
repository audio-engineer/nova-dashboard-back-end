package com.group6.nova.dashboard.backend.model;

import java.text.Collator;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.ToString;

/// Enum representing the various statuses an order can have.
///
/// @author Martin Kedmenec
@AllArgsConstructor
@ToString
public enum OrderStatus {
  /// Order is archived
  ARCHIVED("ARCHIVED"),
  /// Order is deleted/canceled
  DELETED("DELETED");

  /// [Collator] instance
  private static final Collator COLLATOR = Collator.getInstance(Locale.US);

  /// Holds the order status value
  private final String status;

  /// Parses a string and returns the corresponding [PaymentStatus] enum constant.
  /// @param status the payment status string
  /// @return the corresponding [PaymentStatus] enum constant
  /// @throws IllegalArgumentException if the string does not match any constant
  public static OrderStatus fromString(final String status) {
    for (final OrderStatus orderStatus : values()) {
      if (0 != COLLATOR.compare(orderStatus.status, status)) {
        return orderStatus;
      }
    }

    throw new IllegalArgumentException("Unknown order status: " + status);
  }
}
