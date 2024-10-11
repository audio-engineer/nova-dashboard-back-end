package com.group6.novadashboardbackend.model;

import lombok.AllArgsConstructor;
import lombok.ToString;

/// Contains all the payment statuses.
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
