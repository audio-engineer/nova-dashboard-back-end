package com.group6.nova.dashboard.backend.model;

/// DTO for representing a category and its sales for a given timespan.
///
/// @author Martin Kedmenec
public record CategorySalesDto(String name, Long totalSales) {}
