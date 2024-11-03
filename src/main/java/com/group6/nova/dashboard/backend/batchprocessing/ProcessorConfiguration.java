package com.group6.nova.dashboard.backend.batchprocessing;

import com.group6.nova.dashboard.backend.model.Order;
import com.group6.nova.dashboard.backend.model.OrderDto;
import com.group6.nova.dashboard.backend.model.OrderLine;
import com.group6.nova.dashboard.backend.model.OrderLineDto;
import com.group6.nova.dashboard.backend.model.OrderLineMapper;
import com.group6.nova.dashboard.backend.model.OrderMapper;
import com.group6.nova.dashboard.backend.model.Product;
import com.group6.nova.dashboard.backend.repository.OrderLineRepository;
import com.group6.nova.dashboard.backend.repository.OrderRepository;
import com.group6.nova.dashboard.backend.repository.ProductRepository;
import java.util.UUID;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

/// Configuration class for setting up Spring Batch processors for importing orders and order lines.
///
/// @author Martin Kedmenec
@Configuration
@NoArgsConstructor
@ToString
@SuppressWarnings("DesignForExtension")
class ProcessorConfiguration {
  private static @Nullable Product processProduct(
      final OrderLineDto item, final CrudRepository<Product, ? super UUID> productRepository) {
    final UUID productId = item.getProductId();

    if (productRepository.existsById(productId)) {
      return null;
    }

    final String title = item.getTitle();

    return new Product(productId, title);
  }

  private static @Nullable OrderLine processOrderLine(
      final OrderLineDto item,
      final OrderLineMapper orderLineMapper,
      final CrudRepository<OrderLine, ? super UUID> orderLineRepository,
      final OrderRepository orderRepository,
      final ProductRepository productRepository) {
    final UUID orderLineId = item.getId();

    if (orderLineRepository.existsById(orderLineId)) {
      return null;
    }

    return orderLineMapper.toEntityWithOrder(item, orderRepository, productRepository);
  }

  private static @Nullable Order processOrder(
      final OrderDto item,
      final OrderMapper orderMapper,
      final CrudRepository<Order, ? super UUID> orderRepository) {
    final UUID orderId = item.getId();

    if (orderRepository.existsById(orderId)) {
      return null;
    }

    return orderMapper.toEntity(item);
  }

  @Bean
  /* default */ ItemProcessor<OrderDto, Order> orderDuplicateItemProcessor(
      final OrderMapper orderMapper, final OrderRepository orderRepository) {
    return item -> processOrder(item, orderMapper, orderRepository);
  }

  @Bean
  /* default */ ItemProcessor<OrderLineDto, OrderLine> orderLineDuplicateItemProcessor(
      final OrderLineRepository orderLineRepository,
      final OrderLineMapper orderLineMapper,
      final OrderRepository orderRepository,
      final ProductRepository productRepository) {
    return item ->
        processOrderLine(
            item, orderLineMapper, orderLineRepository, orderRepository, productRepository);
  }

  @Bean
  /* default */ ItemProcessor<OrderLineDto, Product> productDuplicateItemProcessor(
      final ProductRepository productRepository) {
    return item -> processProduct(item, productRepository);
  }
}
