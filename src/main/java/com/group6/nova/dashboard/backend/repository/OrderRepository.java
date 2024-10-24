package com.group6.nova.dashboard.backend.repository;

import com.group6.nova.dashboard.backend.model.Order;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/// Repository interface for managing [Order] entities.
///
/// This interface extends [JpaRepository], providing CRUD operations for the [Order] entity.
/// It allows interaction with the database for operations related to orders.
///
/// @author Martin Kedmenec
/// @see Order
/// @see JpaRepository
public interface OrderRepository extends JpaRepository<Order, Long> {
  /// Finds an order by order ID.
  ///
  /// @param orderId the order ID
  /// @return an order, or no result
  Optional<Order> findByOrderId(UUID orderId);
}
