package com.group6.novadashboardbackend.repository;

import com.group6.novadashboardbackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/// Order repository interface.
public interface OrderRepository extends JpaRepository<Order, Long> {}
