package com.group6.nova.dashboard.backend.repository;

import com.group6.nova.dashboard.backend.model.Order;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/// Repository interface for managing [Order] entities.
///
/// @author Martin Kedmenec
public interface OrderRepository extends JpaRepository<Order, UUID> {
  /// Returns all the orders between two given business dates.
  ///
  /// @param startDate start date
  /// @param endDate end date
  /// @param pageable Pageable instance
  /// @return a list of orders
  Page<Order> findByBusinessDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
