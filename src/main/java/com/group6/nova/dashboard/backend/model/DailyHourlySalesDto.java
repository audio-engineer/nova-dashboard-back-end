package com.group6.nova.dashboard.backend.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/// DTO for representing a date and the hourly sales on that specific date.
///
/// @param date the sales date
/// @param hourlySales the sales by hour
/// @author Martin Kedmenec
public record DailyHourlySalesDto(LocalDate date, List<HourlySalesDto> hourlySales) {
  /// Constructor.
  ///
  /// @param date the sales date
  /// @param hourlySales the sales by hour
  public DailyHourlySalesDto {
    hourlySales = Collections.unmodifiableList(hourlySales);
  }

  /// Returns the hourly sales list.
  ///
  /// @return the hourly sales list
  public List<HourlySalesDto> hourlySales() {
    return Collections.unmodifiableList(hourlySales);
  }
}
