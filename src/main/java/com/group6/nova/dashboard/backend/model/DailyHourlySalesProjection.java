package com.group6.nova.dashboard.backend.model;

import java.time.LocalDate;

/// Projection for retrieving the total sales by hour on a given date.
///
/// @author Martin Kedmenec
public interface DailyHourlySalesProjection {
  /// Returns the date of the hourly sales.
  ///
  /// @return the sales date
  LocalDate getDate();

  /// The starting measurement hour.
  ///
  /// @return the starting hour
  Integer getHour();

  /// The total sales for that hour.
  ///
  /// @return the total sales
  Long getTotalSales();
}
