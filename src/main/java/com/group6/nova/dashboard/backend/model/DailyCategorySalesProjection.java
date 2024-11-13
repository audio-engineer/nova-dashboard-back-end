package com.group6.nova.dashboard.backend.model;

import java.time.LocalDate;

/// Projection for retrieving the total category sales on a given date.
///
/// @author Martin Kedmenec
public interface DailyCategorySalesProjection {
  /// Returns the date of the total sales.
  ///
  /// @return the sales date
  LocalDate getDate();

  /// Returns the category name.
  ///
  /// @return the category name
  String getCategoryName();

  /// Returns the total sales on a specific date.
  ///
  /// @return the total sales
  Long getTotalSales();
}
