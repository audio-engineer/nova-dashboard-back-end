package com.group6.nova.dashboard.backend.batchprocessing;

import com.group6.nova.dashboard.backend.model.Order;
import com.group6.nova.dashboard.backend.model.OrderLine;
import com.group6.nova.dashboard.backend.model.Product;
import com.group6.nova.dashboard.backend.repository.OrderLineRepository;
import com.group6.nova.dashboard.backend.repository.OrderRepository;
import com.group6.nova.dashboard.backend.repository.ProductRepository;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/// Configuration class for setting up Spring Batch writers for importing orders and order lines.
///
/// @author Martin Kedmenec
@Configuration
@NoArgsConstructor
@ToString
@SuppressWarnings("DesignForExtension")
class WriterConfiguration {
  @Bean
  /* default */ RepositoryItemWriter<Order> orderRepositoryWriter(
      final OrderRepository orderRepository) {
    final RepositoryItemWriter<Order> writer = new RepositoryItemWriter<>();

    writer.setRepository(orderRepository);
    writer.setMethodName("save");

    return writer;
  }

  @Bean
  /* default */ RepositoryItemWriter<OrderLine> orderLineRepositoryWriter(
      final OrderLineRepository orderLineRepository) {
    final RepositoryItemWriter<OrderLine> writer = new RepositoryItemWriter<>();

    writer.setRepository(orderLineRepository);
    writer.setMethodName("save");

    return writer;
  }

  @Bean
  /* default */ RepositoryItemWriter<Product> productRepositoryWriter(
      final ProductRepository productRepository) {
    final RepositoryItemWriter<Product> writer = new RepositoryItemWriter<>();

    writer.setRepository(productRepository);
    writer.setMethodName("save");

    return writer;
  }
}
