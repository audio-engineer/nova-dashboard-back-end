package com.group6.nova.dashboard.backend.model;

import java.time.LocalDate;
import java.util.List;

/// DTO for representing a date and the sales for each category on that specific date.
///
/// @param date the sales date
/// @param categorySales the sales per category
/// @author Martin Kedmenec
public record DailyCategorySalesDto(LocalDate date, List<CategorySalesDto> categorySales) {}
