package com.group6.nova.dashboard.backend.repository;

import com.group6.nova.dashboard.backend.model.Order;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/// Repository interface for managing [Order] entities.
///
/// @author Martin Kedmenec
public interface OrderRepository extends JpaRepository<Order, UUID> {}
