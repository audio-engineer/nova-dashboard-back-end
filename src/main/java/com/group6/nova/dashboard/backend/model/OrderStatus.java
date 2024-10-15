package com.group6.nova.dashboard.backend.model;

import lombok.AllArgsConstructor;
import lombok.ToString;

/// Enum representing the various statuses an order can have.
///
/// Annotations:
/// - [ToString] - Generates a string representation of the object for debugging.
/// - [AllArgsConstructor] - Generates a constructor for the enum fields.
///
/// @author Martin Kedmenec
@ToString
@AllArgsConstructor
enum OrderStatus {
  /// Order is archived
  ARCHIVED("ARCHIVED"),
  /// Order is deleted/canceled
  DELETED("DELETED");

  /// Holds the order status value
  private final String status;
}
