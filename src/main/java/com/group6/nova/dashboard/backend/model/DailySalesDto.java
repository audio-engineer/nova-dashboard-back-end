package com.group6.nova.dashboard.backend.model;

import java.time.LocalDate;
import java.util.List;

/// DTO for representing a date and the sales for each category on that specific date.
///
/// @author Martin Kedmenec
public record DailySalesDto(LocalDate date, List<CategorySalesDto> categorySales) {}
