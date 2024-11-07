package com.group6.nova.dashboard.backend.model;

import java.time.LocalTime;

/// DTO for representing an hour and its sales.
///
/// @param hour the starting hour
/// @param totalSales the total sales from that hour
/// @author Martin Kedmenec
public record HourlySalesDto(LocalTime hour, Long totalSales) {}
