package com.group6.nova.dashboard.backend.model;

import java.time.LocalDate;

/// Projection used for daily total sales.
///
/// @author Martin Kedmenec
public interface TotalDailySalesProjection {
  /// Returns the date of the total sales.
  ///
  /// @return the sales date
  LocalDate getDate();

  /// Returns the total sales on a specific date.
  ///
  /// @return the total sales
  Long getTotalSales();
}
