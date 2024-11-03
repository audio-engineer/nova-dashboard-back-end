package com.group6.nova.dashboard.backend.model;

import com.group6.nova.dashboard.backend.repository.OrderRepository;
import com.group6.nova.dashboard.backend.repository.ProductRepository;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

/// Mapper interface for converting between [OrderLineDto] and [OrderLine].
///
/// This interface provides a method to map an [OrderLineDto] to an [OrderLine] entity.
/// It uses MapStruct for automatic field mapping and ensures that the mapping is handled by
/// Spring's component model.
///
/// @author Martin Kedmenec
/// @see OrderLine
/// @see OrderLineDto
@Mapper(componentModel = ComponentModel.SPRING)
@FunctionalInterface
public interface OrderLineMapper {
  /// Converts an instance of [OrderLineDto] to its corresponding entity [OrderLine].
  ///
  /// It ignores the autogenerated fields [OrderLine#createdAt] and [OrderLine#updatedAt], and also
  /// the nullable field [OrderLine#order].
  ///
  /// @param orderLineDto OrderLineDto instance
  /// @return OrderLine entity instance
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "order", ignore = true)
  @Mapping(target = "product", ignore = true)
  OrderLine toEntity(OrderLineDto orderLineDto);

  /// Converts an instance of [OrderLineDto] to its corresponding entity [OrderLine], but
  /// additionally searches for an instance of the corresponding [Order] and [Product] instances,
  /// and adds references to the [OrderLine#order] and [OrderLine#product] fields respectively.
  ///
  /// @param orderLineDto OrderLineDto instance
  /// @param orderRepository OrderRepository instance
  /// @param productRepository ProductRepository instance
  /// @return OrderLine entity instance
  default OrderLine toEntityWithOrder(
      final OrderLineDto orderLineDto,
      final OrderRepository orderRepository,
      final ProductRepository productRepository) {
    final OrderLine orderLine = toEntity(orderLineDto);

    final UUID orderId = orderLineDto.getOrderId();
    final Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new NoSuchElementException("Order not found"));

    orderLine.setOrder(order);

    final UUID productId = orderLineDto.getProductId();
    final Product product =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new NoSuchElementException("Product not found"));

    orderLine.setProduct(product);

    return orderLine;
  }
}
