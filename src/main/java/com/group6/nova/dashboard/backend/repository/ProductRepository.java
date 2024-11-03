package com.group6.nova.dashboard.backend.repository;

import com.group6.nova.dashboard.backend.model.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/// Repository interface for managing [Product] entities.
///
/// @author Martin Kedmenec
public interface ProductRepository extends JpaRepository<Product, UUID> {}
