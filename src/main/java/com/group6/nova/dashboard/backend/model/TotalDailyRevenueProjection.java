package com.group6.nova.dashboard.backend.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/// Projection for daily total revenue.
///
/// @author Martin Kedmenec
public interface TotalDailyRevenueProjection {
  /// Returns the business date.
  ///
  /// @return the business date
  LocalDate getDate();

  /// Returns the total revenue for the day.
  ///
  /// @return the total revenue
  BigDecimal getTotalRevenue();
}
