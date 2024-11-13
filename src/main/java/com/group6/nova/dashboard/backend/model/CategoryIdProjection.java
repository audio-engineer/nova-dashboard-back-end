package com.group6.nova.dashboard.backend.model;

import org.springframework.data.rest.core.config.Projection;

/// Projection used for retrieving the category ID only.
///
/// @author Martin Kedmenec
@FunctionalInterface
@Projection(name = "onlyId", types = Category.class)
public interface CategoryIdProjection {
  /// Returns the category ID.
  ///
  /// @return the category ID
  Long getId();
}
