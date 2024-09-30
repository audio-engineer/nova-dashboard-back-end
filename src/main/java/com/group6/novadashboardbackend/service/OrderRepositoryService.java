package com.group6.novadashboardbackend.service;

import com.group6.novadashboardbackend.model.Order;
import com.group6.novadashboardbackend.repository.OrderRepositoryInterface;
import java.util.List;
import lombok.ToString;
import org.springframework.stereotype.Service;

/** Service class for order repository. */
@Service
@ToString
public class OrderRepositoryService {
  /** OrderRepository instance. */
  private final OrderRepositoryInterface orderRepository;

  /**
   * The default OrderRepositoryService constructor.
   *
   * @param orderRepositoryInterface An instance of OrderRepositoryInterface.
   */
  public OrderRepositoryService(final OrderRepositoryInterface orderRepositoryInterface) {
    orderRepository = orderRepositoryInterface;
  }

  /**
   * Returns all persisted orders.
   *
   * @return List of orders.
   */
  public final List<Order> getAll() {
    return (List<Order>) orderRepository.findAll();
  }
}
