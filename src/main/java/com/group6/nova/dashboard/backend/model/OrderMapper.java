package com.group6.nova.dashboard.backend.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

/// Mapper interface for converting between [OrderDto] and [Order].
///
/// This interface provides a method to map an [OrderDto] to an [Order] entity.
/// It uses MapStruct for automatic field mapping and ensures that the mapping is handled by
/// Spring's component model.
///
/// @author Martin Kedmenec
/// @see Order
/// @see OrderDto
@Mapper(componentModel = ComponentModel.SPRING)
@FunctionalInterface
public interface OrderMapper {
  /// Converts an instance of [OrderDto] to its corresponding entity [Order].
  ///
  /// It ignores the autogenerated fields [Order#createdAt] and [Order#updatedAt], and also the
  /// nullable field [Order#orderLines].
  ///
  /// @param orderDto OrderDto instance
  /// @return Order entity instance
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "orderLines", ignore = true)
  Order toEntity(OrderDto orderDto);
}
