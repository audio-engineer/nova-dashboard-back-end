package com.group6.novadashboardbackend.repository;

import com.group6.novadashboardbackend.model.Order;
import org.springframework.data.repository.CrudRepository;

/** Order repository interface. */
public interface OrderRepositoryInterface extends CrudRepository<Order, Long> {}
