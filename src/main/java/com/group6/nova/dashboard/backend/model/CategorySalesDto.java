package com.group6.nova.dashboard.backend.model;

/// DTO for representing a category and its sales for a given timespan.
///
/// @param name the category name
/// @param totalSales the total sales for the category
/// @author Martin Kedmenec
public record CategorySalesDto(String name, Long totalSales) {}
