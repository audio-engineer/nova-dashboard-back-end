package com.group6.nova.dashboard.backend.repository;

import com.group6.nova.dashboard.backend.model.OrderLine;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/// Repository interface for managing [OrderLine] entities.
///
/// @author Martin Kedmenec
public interface OrderLineRepository extends JpaRepository<OrderLine, UUID> {}
