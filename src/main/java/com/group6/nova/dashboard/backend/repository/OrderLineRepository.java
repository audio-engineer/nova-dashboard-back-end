package com.group6.nova.dashboard.backend.repository;

import com.group6.nova.dashboard.backend.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

/// Repository interface for managing [OrderLine] entities.
///
/// This interface extends [JpaRepository], providing CRUD operations for the [OrderLine] entity.
/// It allows interaction with the database for operations related to orders.
///
/// @author Martin Kedmenec
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {}
