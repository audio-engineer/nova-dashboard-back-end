package com.group6.nova.dashboard.backend.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/// Enum representing the supported CSV files used in the import process.
///
/// This enum defines the filename prefixes for the different types of CSV files processed
/// in the application, such as order files and order line files.
///
/// @author Martin Kedmenec
@AllArgsConstructor
@Getter
@ToString
public enum SupportedFiles {
  /// Filename prefix of the order file
  ORDERS_CSV("orders"),

  /// Filename prefix of the order lines file
  ORDER_LINES_CSV("orderlines");

  /// Filename prefix
  private final String fileNamePrefix;
}
