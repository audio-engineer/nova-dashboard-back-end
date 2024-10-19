package com.group6.nova.dashboard.backend.batchprocessing;

import com.group6.nova.dashboard.backend.model.Order;
import lombok.NoArgsConstructor;
import org.springframework.batch.item.ItemProcessor;


/// This Processor should have all the business logic for processing and validating the data
@NoArgsConstructor
public class OrderProcessor implements ItemProcessor<Order, Order> {


  public Order process(Order order) throws Exception {
    return order;
  }
}
