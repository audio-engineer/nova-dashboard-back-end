package com.group6.nova.dashboard.backend.model;

import java.util.UUID;
import org.springframework.data.rest.core.config.Projection;

/// Projection used for retrieving only basic product information such as ID, name and nullable
/// category ID.
///
/// @author Martin Kedmenec
@Projection(name = "withCategory", types = Product.class)
public interface ProductWithCategoryProjection {
  /// Returns the product ID.
  ///
  /// @return the product ID
  UUID getId();

  /// Returns the product name.
  ///
  /// @return the product name
  String getName();

  /// Returns the nested category projection.
  ///
  /// @return the category projection
  CategoryIdProjection getCategory();
}
