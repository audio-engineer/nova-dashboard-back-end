package com.group6.novadashboardbackend.model;

import lombok.AllArgsConstructor;
import lombok.ToString;

/// Contains all the order statuses.
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
