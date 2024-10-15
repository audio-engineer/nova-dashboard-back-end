package com.group6.nova.dashboard.backend.model;

import lombok.AllArgsConstructor;
import lombok.ToString;

/// Enum representing the various payment statuses an order can have.
///
/// Annotations:
/// - [ToString] - Generates a string representation of the object for debugging.
/// - [AllArgsConstructor] - Generates a constructor for the enum fields.
///
/// @author Martin Kedmenec
@ToString
@AllArgsConstructor
enum PaymentStatus {
  /// Order is paid
  PAID("Paid"),
  /// Order is unpaid
  UNPAID("Unpaid");

  /// Holds the payment status value
  private final String status;
}
