package com.group6.nova.dashboard.backend.batchprocessing;

import com.group6.nova.dashboard.backend.model.OrderLine;
import com.group6.nova.dashboard.backend.model.OrderLineDto;
import com.group6.nova.dashboard.backend.model.OrderLineMapper;
import com.group6.nova.dashboard.backend.repository.OrderRepository;
import lombok.ToString;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

/// [FieldSetMapper] implementation for mapping fields from a CSV file to an [OrderLine] entity.
///
/// @author Martin Kedmenec
@Component
@ToString
public class OrderLineFieldSetMapper implements FieldSetMapper<OrderLine> {
  /// [OrderLineMapper] instance
  private final OrderLineMapper orderLineMapper;

  /// [OrderRepository] instance
  private final OrderRepository orderRepository;

  /// Constructor.
  ///
  /// @param orderLineMapperParameter OrderLineMapper instance
  /// @param orderRepositoryParameter OrderRepository instance
  public OrderLineFieldSetMapper(
      final OrderLineMapper orderLineMapperParameter,
      final OrderRepository orderRepositoryParameter) {
    orderLineMapper = orderLineMapperParameter;
    orderRepository = orderRepositoryParameter;
  }

  @Override
  public final OrderLine mapFieldSet(final FieldSet fieldSet) {
    final OrderLineDto orderLineDto = new OrderLineDto(fieldSet);

    return orderLineMapper.toEntityWithOrder(orderLineDto, orderRepository);
  }
}
