package com.group6.nova.dashboard.backend.repository;

import com.group6.nova.dashboard.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/// Repository interface for managing [Category] entities.
///
/// @author Martin Kedmenec
public interface CategoryRepository extends JpaRepository<Category, Long> {}
